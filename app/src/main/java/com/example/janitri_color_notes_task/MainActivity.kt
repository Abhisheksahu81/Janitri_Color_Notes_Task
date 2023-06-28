package com.example.janitri_color_notes_task

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.janitri_color_notes_task.Adapter.ColorAdapter
import com.example.janitri_color_notes_task.Firebase.FirebaseCallback
import com.example.janitri_color_notes_task.RoomDB.ColorEntity
import com.example.janitri_color_notes_task.RoomDB.MyApplication
import com.example.janitri_color_notes_task.Repository.ColorRepository
import com.example.janitri_color_notes_task.ViewModel.ColorViewModel
import com.example.janitri_color_notes_task.ViewModelFactory.ColorViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class MainActivity : AppCompatActivity(), FirebaseCallback {
    private lateinit var colorViewModel: ColorViewModel
    private lateinit var rv : RecyclerView
    private lateinit var colorAdapter: ColorAdapter

    private lateinit var add_note : TextView
    private lateinit var handler : Handler

    private lateinit var synced_tv : TextView
    private lateinit var sync_btn : ConstraintLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_note = findViewById(R.id.add_note)
        synced_tv = findViewById(R.id.number_tv)
        sync_btn = findViewById(R.id.sync_button)

        window.statusBarColor = Color.parseColor("#5659a4")

        rv = findViewById(R.id.recyclerView)
        colorAdapter = ColorAdapter(ArrayList<ColorEntity>() , this)
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = colorAdapter

        handler = Handler(Looper.getMainLooper())
        val colorDao = MyApplication.database.colorDao()
        val colorRepository = ColorRepository(colorDao)
        val viewModelFactory = ColorViewModelFactory(colorRepository)
        colorViewModel = ViewModelProvider(this, viewModelFactory).get(ColorViewModel::class.java)

        set_sync_tv()
        // Observe the colors LiveData
        colorViewModel.colors.observe(this, Observer { colors ->
            colorAdapter.update_data(colors)
            colorAdapter.notifyDataSetChanged()
            set_sync_tv()

        })

        add_note.setOnClickListener(View.OnClickListener {
           //generate random color

            val generated_color = generateRandomColor();

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = Date()
            val created_date =  dateFormat.format(currentDate)

            var color = ColorEntity(colorValue = generated_color , isSynced = "no" , createdAt = created_date)
            colorViewModel.insertColor(color)
            Toast.makeText(this, "Color Note Genrated of color : ${generated_color} ", Toast.LENGTH_LONG).show()
            colorViewModel.getAllColors()

        })

        sync_btn.setOnClickListener {

            Toast.makeText(this, "Syncing to Google Firebase", Toast.LENGTH_LONG).show()
            colorViewModel.firebase_sync(this)

        }

        // Retrieve all colors
        colorViewModel.getAllColors()


    }
    fun set_sync_tv()
    {
        var c = 0
        val colors = colorViewModel._colors.value
        if (colors != null) {
            for(color in colors)
            {
                if(color.isSynced.equals("no"))
                    c++;
            }
        }
        synced_tv.setText(c.toString())
    }
    fun generateRandomColor(): String {
        val random = Random(System.currentTimeMillis())
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)


        return String.format("#%06X", 0xFFFFFF and Color.rgb(red, green, blue))
    }

    override fun onResume() {
        super.onResume()
        colorViewModel.getAllColors()

    }

    override fun callback() {
        colorViewModel._colors.value?.forEach {
            it.isSynced = "yes"
            colorViewModel.updateColor(it)
        }
        Log.e("Learning Callback","Callback from Firebase is implemented in mainactivity")
        handler.post{
            set_sync_tv()
        }

    }
}

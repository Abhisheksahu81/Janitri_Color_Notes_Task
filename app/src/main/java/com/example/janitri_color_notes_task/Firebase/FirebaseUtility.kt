import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.janitri_color_notes_task.Firebase.FirebaseCallback
import com.example.janitri_color_notes_task.RoomDB.ColorEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.callbackFlow


class FirebaseUtility()
{
    private lateinit var mAuth: FirebaseAuth
    private var callback: FirebaseCallback? = null
    suspend fun Update_database(list: MutableLiveData<List<ColorEntity>>)
    {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInAnonymously().addOnCompleteListener({

            if (it.isSuccessful) {
                Log.e("Firebase_login", "Login Success")
                val user: FirebaseUser? = mAuth.currentUser
                val userId: String = user?.uid ?: ""
                Log.e("Firebase_login" , userId)

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("data")


                list.value?.forEach {color ->
                    if(color.isSynced.equals("no"))
                    {
//                        Log.e("Colors in Firebase to be added" , "${color}")
                        myRef.child(userId).child(color.id.toString()).setValue(color.colorValue)
                            .addOnCompleteListener({
                                Log.e("Firebase_login" , "Hello this")
                                if(it.isSuccessful)
                                {
                                    Log.e("Firebase_login" , "Data added succesfully")
                                }
                                else
                                {
                                    Log.e("Firebase_login" , "Data not added ${it.exception}")
                                }

                            }).addOnFailureListener {
                                Log.e("Firebase_login" , "Error is  : ${it}")
                            }
                        Log.e("Colors in Firebase to be added","Hello ")

                    }
                    else{
                        Log.e("Colors in Firebase added" , "${color}")
                    }
                }

            } else {

                Log.e("Firebase_login", "signInAnonymously:failure", it.exception)

            }
        })

        callback?.callback()

    }
    fun setFirbasecallback(callback : FirebaseCallback)
    {
        Log.e("Learning callback", "callback set up")
        this.callback = callback
    }


}
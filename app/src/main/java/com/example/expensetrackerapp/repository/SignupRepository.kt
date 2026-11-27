package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.model.User
import com.example.expensetrackerapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SignupRepository @Inject constructor(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun signUp(user: User,password:String,onResult:(Resource) -> Unit)
    {

        auth.createUserWithEmailAndPassword(user.email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    task.result.user?.let { firebaseUser ->
                        saveUser(firebaseUser.uid,user){isSaved ->

                            if (isSaved)
                            {
                                auth.signOut()
                                onResult(Resource.Success)
                            }else{
                                firebaseUser.delete().addOnCompleteListener { deleteTask->
                                    if (deleteTask.isSuccessful)
                                    {
                                        onResult(Resource.Failure("failed to save user"))
                                    }
                                    else{
                                        onResult(Resource.Failure(deleteTask.exception?.message?:"sign up failed"))
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    onResult(Resource.Failure(
                        task.exception?.message.toString()
                    ))
                }

            }
    }

    private fun saveUser(uId: String,user: User,isSaved:(Boolean) -> Unit)
    {
        db.collection("users")
            .document(uId)
            .set(user)
            .addOnSuccessListener {
                isSaved(true)
            }
            .addOnFailureListener {
                isSaved(false)
            }
    }

}
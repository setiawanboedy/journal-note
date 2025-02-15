package com.tafakkur.diaryapp.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tafakkur.diaryapp.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    authenticated: Boolean,
    loadingState: Boolean,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onSuccessfullyFirebaseSign: (String)->Unit,
    onFailedFirebaseSign: (Exception)->Unit,
    onDialogDismiss: (String)->Unit,
    onButtonClicked: ()->Unit,
    navigateToHome: ()->Unit
) {
    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        ContentWithMessageBar(messageBarState = messageBarState) {
            AuthenticationContent(
                loadingState = loadingState,
                onButtonClicked = onButtonClicked
            )
        }
    }

    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = {token ->
           val credential = GoogleAuthProvider.getCredential(token, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        onSuccessfullyFirebaseSign(token)
                    }else{
                        task.exception?.let {
                            onFailedFirebaseSign(it)
                        }
                    }
                }
    }, onDialogDismissed = {message->
            onDialogDismiss(message)
        })
    
    LaunchedEffect(key1 = authenticated){
        if (authenticated){
            navigateToHome()
        }
    }
}

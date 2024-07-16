package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.util.DiaryHolder
import com.example.util.model.Diary
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    diaryNotes: Map<LocalDate, List<Diary>>,
    onClicked: (String) -> Unit,
) {
    if (diaryNotes.isNotEmpty()){
        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = paddingValues.calculateTopPadding())
        ){
            diaryNotes.forEach{(localDate, diaries)->
                stickyHeader(key = localDate) { 
                    DateHeader(localDate = localDate)
                }
                items(
                    items = diaries,
                    key = {it._id.toString()}
                ){
                    DiaryHolder(diary = it, onClicked = onClicked)
                }
            }
        }
    }else{
        EmptyPage()
    }
}

@SuppressLint("NewApi")
@Composable
internal fun DateHeader(
    localDate: LocalDate
) {
    Row(
        modifier = Modifier.padding(vertical = 14.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = String.format(Locale.ROOT,"%02d", localDate.dayOfMonth),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
            Text(
                text = localDate.dayOfWeek.toString().take(3),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = localDate.month.toString().lowercase()
                    .replaceFirstChar { it.lowercase() },
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
            Text(
                text = "${localDate.year}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
        }
    }
}

@Composable
fun EmptyPage(
    title: String = "Empty Page",
    subtitle: String = "Write Something"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium,
            ),
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@SuppressLint("NewApi")
@Composable
@Preview
fun DateHeaderPreview() {
    DateHeader(localDate = LocalDate.now())
}
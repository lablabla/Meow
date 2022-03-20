package com.lablabla.meow.presentation.user_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lablabla.meow.domain.model.MeowUser

@Composable
fun UserItem(
    user: MeowUser,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = user.drawable),
                contentDescription = "User Icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = user.name,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 32.dp)
            )
        }
    }
}
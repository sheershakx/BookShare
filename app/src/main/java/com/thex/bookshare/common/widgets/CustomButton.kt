package com.thex.bookshare.common.widgets


import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceDim,
        contentColor = Color.White,
        disabledContentColor = Color.White
    ),
    buttonName: String,
    iconPadding: PaddingValues = PaddingValues(start = 0.dp, end = 8.dp, top = 1.dp, bottom = 0.dp),
    buttonHeight: Dp = 56.dp,
    buttonRadius: Dp = 300.dp,
    buttonElevation: Dp = 0.dp,
    contentColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight(800),
    fontSize: TextUnit = 17.sp,
    lineHeight: TextUnit = 22.sp,
    arrangement: Arrangement.Horizontal = Arrangement.Center,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    contentStyle: TextStyle = TextStyle(
        color = contentColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        lineHeight = lineHeight
    ),
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .indication(interactionSource = interactionSource, indication = null),
        elevation = ButtonDefaults.buttonElevation(buttonElevation),
        onClick = {
            if (enabled && !isLoading) {
                onClick()
            }
        },
        shape = RoundedCornerShape(buttonRadius),
        colors = buttonColor,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangement
        ) {
            if (leadingIcon != null) {
                Icon(
                    modifier = Modifier.padding(iconPadding),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "button icon $buttonName",
                    tint = Color.Unspecified
                )
            }
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = buttonName,
                    style = contentStyle,
//                    fontFamily = ManRopeFonts,
                )
                if (trailingIcon != null) {
                    Icon(
                        modifier = Modifier.padding(iconPadding),
                        painter = painterResource(id = trailingIcon),
                        contentDescription = "button icon $buttonName",
                        tint = Color.Unspecified
                    )
                }
            }

        }

    }
}

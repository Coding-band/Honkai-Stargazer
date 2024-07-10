package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import types.Constants
import types.Constants.Companion.MATERIAL_CARD_HEIGHT
import types.Constants.Companion.MATERIAL_CARD_WIDTH
import types.Material
import utils.FontSizeNormal12
import utils.TextColorNormalDim

@Composable
fun MaterialCard(
    material: Material,
    onClick: () -> Unit = {}, //按下後會做甚麼
    isDisplayCount: Boolean = true

) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .defaultMinSize(MATERIAL_CARD_WIDTH, MATERIAL_CARD_HEIGHT)
            .clip(
                RoundedCornerShape(
                    topEnd = 15.dp,
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                )
            )
            .background(
                Brush.verticalGradient(
                    colors = Constants.getCardBgColorByRare(material.rarity)
                )
            )
            .clickable(
                onClick = onClick,
                indication = rememberRipple(),
                interactionSource = interactionSource
            )
    ) {
        Column(modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                Image(
                    bitmap = Material.getMaterialImageById(material.officialId),
                    contentDescription = "Material Icon",
                    modifier = Modifier
                        .padding(6.dp)
                        .aspectRatio(1f)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF222222)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isDisplayCount) material.count.toString() else material.name!!,
                    textAlign = TextAlign.Center,
                    color = TextColorNormalDim,
                    fontSize = FontSizeNormal12().fontSize,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
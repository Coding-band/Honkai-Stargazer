import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)? = null,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.clip(CircleShape).clickable(onClick = onClick)){
            Box(modifier = Modifier.padding(8.dp)){
                icon()
            }
        }

        if (label != null) {
            Spacer(modifier = Modifier.height(4.dp))
            label()
        }
    }
}

@Composable
fun NavigationBar(
    items: List<NavigationItemData>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = { Icon(item.icon, contentDescription = null, tint = if (selectedIndex == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface, modifier = Modifier.size(24.dp)) },
                label = { Text(item.label, color = if (selectedIndex == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)) }
            )
        }
    }
}

data class NavigationItemData(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)


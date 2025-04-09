import android.R.attr.textSize
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jobfinder.R
import com.example.jobfinder.utils.MyColorUtils

@Composable
fun BoxBackground( titleHeading1: String, titleHeading2: String ) {
    Box(
        modifier = Modifier.height(IntrinsicSize.Max) //max height of children's components
    ) {
        ProductBackground(
            Modifier.padding(bottom = 24.dp)
        )

        Content(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 24.dp),
            titleHeading1 = titleHeading1,
            titleHeading2 = titleHeading2
        )
    }
}


@Composable
private fun ProductBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MyColorUtils.PurpleBanner,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ),
    ){
    }
}


@Composable
private fun Content(
    modifier: Modifier = Modifier,titleHeading1: String , titleHeading2: String
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (actionBar, productImg,text_title, text_small_title) = createRefs()

        Text(text = "$titleHeading1", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs (text_title){
                start.linkTo(parent.start, margin = 24.dp)
                top.linkTo(parent.top, margin = 24.dp)
            })

        Text(text = "$titleHeading2", fontSize = 14.sp, color = Color.White,
            modifier = Modifier.constrainAs (text_small_title){
                start.linkTo(parent.start, margin = 24.dp)
                top.linkTo(text_title.bottom, margin = 4.dp)
            })

        Image(
            painter = painterResource(R.drawable.alarm_clock),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(128.dp)
                .constrainAs(productImg) {
                    end.linkTo(parent.end)
                    top.linkTo(anchor = actionBar.bottom, margin = 20.dp)
                }
        )

    }
}



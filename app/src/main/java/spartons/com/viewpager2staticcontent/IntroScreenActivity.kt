package spartons.com.viewpager2staticcontent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import spartons.com.viewpager2staticcontent.adapter.IntroAdapter
import spartons.com.viewpager2staticcontent.util.listenForAllSpringsEnd
import spartons.com.viewpager2staticcontent.util.spring

class IntroScreenActivity : AppCompatActivity() {

    private val viewPagerOnChangeCallback= object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            introScreenCenterImageView.spring(SpringAnimation.ALPHA).apply {
                animateToFinalPosition(0f)
            }.also { springAnimation ->
                listenForAllSpringsEnd({
                    springAnimation.animateToFinalPosition(1f)
                    introScreenCenterImageView.setImageResource(centeredImageResources[position])
                }, springAnimation)
            }
        }
    }

    private companion object {
        private val centeredImageResources = mutableListOf(
            R.drawable.first_background_image,
            R.drawable.second_background_image,
            R.drawable.third_background_image
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IntroAdapter().apply {
            submitList(centeredImageResources)
        }.also { introScreenViewPager.adapter = it }

        wormIndicator.setViewPager2(introScreenViewPager)

        introScreenViewPager.registerOnPageChangeCallback(viewPagerOnChangeCallback)
    }
}

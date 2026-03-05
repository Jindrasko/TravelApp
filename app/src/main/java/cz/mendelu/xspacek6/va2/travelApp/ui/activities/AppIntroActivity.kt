package cz.mendelu.xspacek6.va2.travelApp.ui.activities

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import cz.mendelu.xspacek6.va2.travelApp.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppIntroActivity : AppIntro(){

    private val viewModel: AppIntroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isWizardMode = true

        addSlide(
            AppIntroFragment.createInstance(
                title = getString(R.string.intro1T),
                description = getString(R.string.intro1D),
                backgroundColorRes = R.color.backgroundDark,
                imageDrawable = R.drawable.tower
            ))
        addSlide(AppIntroFragment.createInstance(
            title = getString(R.string.intro2T),
            description = getString(R.string.intro2D),
            backgroundColorRes = R.color.backgroundDark,
            imageDrawable = R.drawable.location_icon
        ))
        addSlide(AppIntroFragment.createInstance(
            title = getString(R.string.intro3T),
            description = getString(R.string.intro3D),
            backgroundColorRes = R.color.backgroundDark,
            imageDrawable = R.drawable.camera_icon
        ))
        addSlide(AppIntroFragment.createInstance(
            title = getString(R.string.intro4T),
            description = getString(R.string.intro4D),
            imageDrawable = R.drawable.check_mark
            ,
            backgroundColorRes = R.color.backgroundDark
        ))

        askForPermissions(
            permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            slideNumber = 2,
            required = true
        )

        askForPermissions(
            permissions = arrayOf(Manifest.permission.CAMERA),
            slideNumber = 3,
            required = true
        )

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        continueToMainActivity()
    }

    private fun continueToMainActivity() {
        lifecycleScope.launch {
            viewModel.setFirstRun()
        }.invokeOnCompletion {
            finish()
        }
    }

}
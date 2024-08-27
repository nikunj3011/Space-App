package space

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import space.animations.Fade
import space.animations.animationXFade
import space.databinding.ActivityDiscoverBinding
import space.databinding.ActivityHomeBinding


class DiscoverActivity : AppCompatActivity() {
    private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private var isDiscoveryCompleted:Boolean = false
    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sf = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        editor = sf.edit()
        binding = ActivityDiscoverBinding.inflate((layoutInflater))
        enableEdgeToEdge()
        setContentView(R.layout.activity_discover)
        if(sf.getBoolean("discoveryCompleted",false)){
            val intent = Intent(this@DiscoverActivity,HomeActivity::class.java)
            startActivity(intent)
        }
        val button: Button = findViewById<Button>(R.id.btnGoToHome)
        // Register the onClick listener with the implementation above
        button.setOnClickListener { view ->
            val intent = Intent(this@DiscoverActivity,HomeActivity::class.java)
            editor.apply{
                isDiscoveryCompleted = true
                putBoolean("discoveryCompleted", isDiscoveryCompleted)
                commit()
            }
            startActivity(intent)
        }

//        binding.videoView.setVideoURI(
//            Uri.parse("android.resource://${packageName}/${R.raw.blackhole}")
//        )
//        binding.videoView.start()
//        binding.videoView.setOnPreparedListener { mp ->
//            mp.isLooping = true
//            mp.start()
//        }
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.blackhole)
        val videoView = findViewById<VideoView>(R.id.videoView)

//        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.blackhole}")
        try {
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            }
            videoView.start()
        } catch (e: Exception) {
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
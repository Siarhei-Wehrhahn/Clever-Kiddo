package com.example.kiinderlernapp.ui.tamagochi

import TamagotchiService
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import com.example.kiinderlernapp.databinding.FragmentTamagochiBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TamagochiFragment : Fragment() {

    private lateinit var binding: FragmentTamagochiBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var tamagotchiService: TamagotchiService? = null
    private val tamagotchi = Tamagotchi(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var isScreenBlocked = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TamagotchiService
            tamagotchiService = binder.getSystemService()

            viewModel.tamagotchi.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    updateDatabase(tamagotchi)
                    viewModel.insertTamagotchiStats(tamagotchi)
                }
                viewModel.tamagotchi.value.let {
                    binding.textHappinesPercent.text = it?.joy.toString()
                    binding.textHungryPercent.text = it?.eat.toString()
                    binding.textSleepPercent.text = it?.sleep.toString()
                    binding.textToiletPercent.text = it?.toilet.toString()
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            tamagotchiService = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTamagochiBinding.inflate(inflater, container, false)
        val serviceIntent = Intent(requireContext(), TamagotchiService::class.java)
        requireContext().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDataTamagotchi()

        val joy = viewModel.tamagotchi.value?.joy.toString().toInt()
        val toilet = viewModel.tamagotchi.value?.toilet.toString().toInt()
        val eat = viewModel.tamagotchi.value?.eat.toString().toInt()
        val sleep = viewModel.tamagotchi.value?.sleep.toString().toInt()

        val serviceIntent = Intent(requireContext(), TamagotchiService::class.java)
        requireContext().startService(serviceIntent)

        // Je nach status macht es ein anderes Gesicht
        viewModel.tamagotchi.observe(viewLifecycleOwner) {
            if (joy < (50..65).random() && toilet > 15 && eat > 30 && sleep > 20) {
                binding.imageTamagotchi.setImageResource(R.drawable.neutral)
            } else if (joy < (30..40).random() && toilet > 15 && eat > 30 && sleep > 20) {
                binding.imageTamagotchi.setImageResource(R.drawable.angry)
            } else {
                binding.imageTamagotchi.setImageResource(R.drawable.scared)
            }

            if (eat < (15..30).random() && toilet > 15 && joy > 30 && sleep > 20) {
                binding.imageTamagotchi.setImageResource(R.drawable.shoked)
            } else {
                binding.imageTamagotchi.setImageResource(R.drawable.scared)
            }

            if (joy < 20 && toilet < 15 && eat < 30 && sleep < 20) {
                binding.imageTamagotchi.setImageResource(R.drawable.angryred)
            } else {
                binding.imageTamagotchi.setImageResource(R.drawable.scared)
            }

            if (sleep < 20 && toilet > 15 && eat > 30 && joy > 20) {
                binding.imageTamagotchi.setImageResource(R.drawable.neutral)
            } else {
                binding.imageTamagotchi.setImageResource(R.drawable.scared)
            }
        }

        binding.foodButton.setOnClickListener {
            binding.imageFotball.isVisible = false
            binding.imageTennisball.isVisible = false
            if (binding.feddingScrollV.scaleX == 1f) {
                val scaleUp = ObjectAnimator.ofFloat(binding.feddingScrollV, "scaleX", 0.01f)
                val animation = AnimatorSet()
                animation.duration = 500
                animation.play(scaleUp)
                animation.start()
                lifecycleScope.launch {
                    delay(500)
                    binding.feddingScrollV.isVisible = false
                }
            } else {
                val scaleUp = ObjectAnimator.ofFloat(binding.feddingScrollV, "scaleX", 1.0f)
                val animation = AnimatorSet()
                animation.duration = 500
                binding.feddingScrollV.isVisible = true
                animation.play(scaleUp)
                animation.start()
            }
        }

        binding.buttonPlay.setOnClickListener {
            if (binding.feddingScrollV.scaleX == 1f) {
                val scaleUp = ObjectAnimator.ofFloat(binding.feddingScrollV, "scaleX", 0.01f)
                val animation = AnimatorSet()
                animation.duration = 500
                animation.play(scaleUp)
                animation.start()
                lifecycleScope.launch {
                    delay(500)
                    binding.feddingScrollV.isVisible = false
                }
            }
            if (!binding.imageFotball.isVisible && !binding.imageTennisball.isVisible) {
                binding.imageFotball.isVisible = true
                binding.imageTennisball.isVisible = true
            } else {
                binding.imageFotball.isVisible = false
                binding.imageTennisball.isVisible = false
            }
        }

        binding.buttonToilet.setOnClickListener {
            binding.imageTamagotchi.setImageResource(R.drawable.nerdy_grinnging)
            val window = requireActivity().window
            val params = window.attributes

            // Bildschirm blockieren
            params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            window.attributes = params

            isScreenBlocked = true

            // Hier füge deine Verzögerungslogik ein, z.B. eine Coroutine mit delay
            lifecycleScope.launch {
                delay(10000) // Blockiere den Bildschirm für 5 Sekunden
                // Bildschirm freigeben
                binding.imageTamagotchi.setImageResource(R.drawable.happy)
                params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                window.attributes = params
                isScreenBlocked = false
            }
        }

        binding.imageTennisball.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("tennisball", "tennisball")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.INVISIBLE
                true
            } else {
                false
            }
        }

        binding.imageFotball.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "football")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.INVISIBLE
                true
            } else {
                false
            }
        }

        binding.imageApple.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "apple")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageBroccoli.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "broccoli")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imagePeas.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "peas")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageStrawberry.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "strawberry")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imagePomegrenade.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "pomegrenade")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageCucumber.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "cucumber")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageKiwi.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "kiwi")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageSalat.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "salat")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = View.GONE
                true
            } else {
                false
            }
        }

        binding.imageTamagotchi.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val draggedItem = event.clipData.getItemAt(0).text.toString()

                    when (draggedItem) {
                        // TODO Werte höher setzen nachdem etwas konsumiert wurde
                        // Reaktion für Tennisball
                        "tennisball" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.haha)
                            jump()
                            bigDelayToNormal()
                        }

                        // Reaktion für Fußball
                        "football" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.smart)
                            jump()
                            bigDelayToNormal()
                        }

                        "apple" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                            smallDelayToNormal()
                        }

                        "broccoli" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.alien)
                            smallDelayToNormal()
                        }

                        "peas" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.gringing2)
                            smallDelayToNormal()
                        }

                        "strawberry" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                            smallDelayToNormal()
                        }

                        "pomegrenade" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                            jump()
                            smallDelayToNormal()
                        }

                        "cucumber" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.gringing2)
                            smallDelayToNormal()
                        }

                        "kiwi" -> {
                            binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                            smallDelayToNormal()
                        }

                        "salat" -> {
                            repeat(5) {
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                smallDelayToNormal()
                            }
                        }
                    }
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    // Hier können Sie den Fußball wieder sichtbar machen, wenn gewünscht
                    if (!event.result) {
                        binding.imageFotball.visibility = View.VISIBLE
                    }
                    true
                }

                else -> true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unbindService(serviceConnection)
    }


    fun jump() {
        var jumpUp = TranslateAnimation(0f, 0f, 0f, -400f)
        var rotation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        rotation.startOffset = 200
        jumpUp.duration = 200
        var animationSet = AnimationSet(true)
        animationSet.addAnimation(jumpUp)
        animationSet.addAnimation(rotation)
        animationSet.duration = 800
        binding.imageTamagotchi.startAnimation(animationSet)
    }

    private suspend fun updateDatabase(tamagotchi: Tamagotchi) {
        viewModel.updateTamagotchi(tamagotchi)
    }

    private fun bigDelayToNormal() {
        lifecycleScope.launch {
            delay(1000)
            binding.imageTamagotchi.setImageResource(R.drawable.happy)
        }
    }

    private fun smallDelayToNormal() {
        lifecycleScope.launch {
            delay(200)
            binding.imageTamagotchi.setImageResource(R.drawable.happy)
        }
    }

    override fun onResume() {
        super.onResume()

        // Setze den OnBackPressedCallback
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isScreenBlocked) {

                    // Alert
                    AlertDialog.Builder(requireContext())
                        .setMessage("Möchten sie die App schließen?")
                        .setPositiveButton("Ja") { _, _, ->
                            requireActivity().finish()
                        }
                        .setNegativeButton("Nein", null)
                        .show()
                } else {

                    // Wenn der Bildschirm nicht blockiert ist, handle das Zurückswipen normal
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    // Entferne den OnBackPressedCallback(zurück)
    override fun onPause() {
        super.onPause()
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}
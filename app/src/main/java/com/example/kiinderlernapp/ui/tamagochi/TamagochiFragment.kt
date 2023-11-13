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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private val tamagotchi = Tamagotchi(1, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    private var isScreenBlocked = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TamagotchiService
            tamagotchiService = binder.getSystemService()
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

        binding.buttonSleep.setOnClickListener {
            viewModel.addItem("apple")
        }

        // Ohne null prüfung stürzt die app ab
        val joy = viewModel.tamagotchi.value?.joy?.toString()?.toIntOrNull() ?: 0
        val toilet = viewModel.tamagotchi.value?.toilet?.toString()?.toIntOrNull() ?: 0
        val eat = viewModel.tamagotchi.value?.eat?.toString()?.toIntOrNull() ?: 0
        val sleep = viewModel.tamagotchi.value?.sleep?.toString()?.toIntOrNull() ?: 0

        viewModel.tamagotchi.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                updateDatabase(it)
                viewModel.insertTamagotchiStats(it)
            }
            it.let {
                binding.textHappinesPercent.text = it?.joy.toString() + "%"
                binding.textHungryPercent.text = it?.eat.toString() + "%"
                binding.textSleepPercent.text = it?.sleep.toString() + "%"
                binding.textToiletPercent.text = it?.toilet.toString() + "%"
            }

            if (tamagotchi != null) {
                if (tamagotchi.apple == 0) {
                    binding.imageApple.visibility = GONE
                } else {
                    binding.imageApple.visibility = VISIBLE
                }
                if (tamagotchi.broccoli == 0) {
                    binding.imageBroccoli.visibility = GONE
                } else {
                    binding.imageBroccoli.visibility = VISIBLE
                }
                if (tamagotchi.peas == 0) {
                    binding.imagePeas.visibility = GONE
                } else {
                    binding.imagePeas.visibility = VISIBLE
                }
                if (tamagotchi.strawberry == 0) {
                    binding.imageStrawberry.visibility = GONE
                } else {
                    binding.imageStrawberry.visibility = VISIBLE
                }
                if (tamagotchi.pomegrenade == 0) {
                    binding.imagePomegrenade.visibility = GONE
                } else {
                    binding.imagePomegrenade.visibility = VISIBLE
                }
                if (tamagotchi.cucumber == 0) {
                    binding.imageCucumber.visibility = GONE
                } else {
                    binding.imageCucumber.visibility = VISIBLE
                }
                if (tamagotchi.kiwi == 0) {
                    binding.imageKiwi.visibility = GONE
                } else {
                    binding.imageKiwi.visibility = VISIBLE
                }
            }
        }

        binding.foodButton.setOnClickListener {
            binding.imageToiletpaper.visibility = GONE
            val tamagotchi = viewModel.tamagotchi.value
            binding.imageFotball.isVisible = false
            binding.imageTennisball.isVisible = false
            if (tamagotchi?.apple != 0 ||
                tamagotchi?.broccoli != 0 ||
                tamagotchi?.peas != 0 ||
                tamagotchi?.strawberry != 0 ||
                tamagotchi?.pomegrenade != 0 ||
                tamagotchi?.cucumber != 0 ||
                tamagotchi?.kiwi != 0) {
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
            } else {
                Toast.makeText(requireContext(),"Du hast leider nicht zu Essen!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonPlay.setOnClickListener {
            binding.imageToiletpaper.visibility = GONE
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
            if (!binding.imageFotball.isVisible && viewModel.tamagotchi.value?.footBall!! > 0) {
                binding.imageFotball.visibility = VISIBLE
            } else {
                binding.imageFotball.visibility = GONE
            }
            if (!binding.imageTennisball.isVisible && viewModel.tamagotchi.value?.tennisBall!! > 0) {
                binding.imageTennisball.visibility = VISIBLE
            } else {
                binding.imageTennisball.visibility = GONE
            }
            if (viewModel.tamagotchi.value?.footBall!! == 0 && viewModel.tamagotchi.value?.tennisBall!! == 0) {
                Toast.makeText(requireContext(),"Du hast leider keine Bälle!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonToilet.setOnClickListener {
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
            }
            if (viewModel.tamagotchi.value?.toilet!! > 0 && !binding.imageToiletpaper.isVisible) {
                binding.imageToiletpaper.visibility = VISIBLE
            } else {
                binding.imageToiletpaper.visibility = GONE
            }
        }

        binding.imageToiletpaper.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "toilet_paper")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = VISIBLE
                true
            } else {
                false
            }
        }

        binding.imageTennisball.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val clipData = ClipData.newPlainText("", "tennisball")
                val dragShadowBuilder = DragShadowBuilder(view)
                view.startDragAndDrop(clipData, dragShadowBuilder, view, 0)
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                view.visibility = VISIBLE
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
                        // Reaktion für Tennisball
                        "tennisball" -> {
                            if (viewModel.tamagotchi.value?.joy!! < 85) {
                                viewModel.removeItem("tennisball")
                                binding.imageTamagotchi.setImageResource(R.drawable.haha)
                                jump()
                                bigDelayToNormal()
                            }
                        }

                        // Reaktion für Fußball
                        "football" -> {
                            if (viewModel.tamagotchi.value?.joy!! < 80) {
                                viewModel.removeItem("football")
                                binding.imageTamagotchi.setImageResource(R.drawable.smart)
                                jump()
                                bigDelayToNormal()
                            }
                        }

                        "apple" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 95) {
                                viewModel.removeItem("apple")
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                smallDelayToNormal()
                            }
                        }

                        "broccoli" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 90) {
                                viewModel.removeItem("broccoli")
                                binding.imageTamagotchi.setImageResource(R.drawable.alien)
                                smallDelayToNormal()
                            }
                        }

                        "peas" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 90) {
                                viewModel.removeItem("peas")
                                binding.imageTamagotchi.setImageResource(R.drawable.gringing2)
                                smallDelayToNormal()
                            }
                        }

                        "strawberry" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 95) {
                                viewModel.removeItem("strawberry")
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                smallDelayToNormal()
                            }
                        }

                        "pomegrenade" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 80) {
                                viewModel.removeItem("pomegrenade")
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                jump()
                                smallDelayToNormal()
                            }
                        }

                        "cucumber" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 90) {
                                viewModel.removeItem("cucumber")
                                binding.imageTamagotchi.setImageResource(R.drawable.gringing2)
                                smallDelayToNormal()
                            }
                        }

                        "kiwi" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 95) {
                                viewModel.removeItem("kiwi")
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                smallDelayToNormal()
                            }
                        }

                        "salat" -> {
                            if (viewModel.tamagotchi.value?.eat!! < 90) {
                                viewModel.removeItem("salat")
                                binding.imageTamagotchi.setImageResource(R.drawable.grinning)
                                smallDelayToNormal()
                            }
                        }

                        "toilet_paper" -> {
                            if (viewModel.tamagotchi.value?.toilet!! < 90) {
                                binding.imageToiletpaper.visibility = GONE

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
                                    viewModel.removeItem("toilet_paper")
                                    // Bildschirm freigeben
                                    binding.imageTamagotchi.setImageResource(R.drawable.happy)
                                    params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                                    window.attributes = params
                                    isScreenBlocked = false
                                }
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
            delay(1000)
            binding.imageTamagotchi.setImageResource(R.drawable.happy)
        }
    }

    override fun onResume() {
        super.onResume()

        // Setze den OnBackPressedCallback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isScreenBlocked) {

                    // Alert
                    AlertDialog.Builder(requireContext())
                        .setMessage("Möchten sie die App schließen?")
                        .setPositiveButton("Ja") { _, _ ->
                            requireActivity().finish()
                        }
                        .setNegativeButton("Nein", null)
                        .show()
                } else {

                    // Wenn der Bildschirm nicht blockiert ist, handle das Zurückswipen normal
                    isEnabled = false
                    findNavController().popBackStack()
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
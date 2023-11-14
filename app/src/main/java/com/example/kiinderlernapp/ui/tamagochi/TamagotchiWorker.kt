import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.kiinderlernapp.data.AppRepository
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import com.example.kiinderlernapp.data.localdata.animal.AnimalDataBase
import com.example.kiinderlernapp.data.localdata.tamagotchi.TamagotchiDataBase
import com.example.kiinderlernapp.data.remote.CatApi
import com.example.kiinderlernapp.data.remote.DogApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TamagotchiWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val repo = AppRepository(
        DogApi,
        CatApi,
        AnimalDataBase.getDatabase(context),
        TamagotchiDataBase.getDatabase(context)
    )

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            decreaseTamagotchiValues()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun decreaseTamagotchiValues() {
        val currentTamagotchi = repo.tamagotchi.value

        if (currentTamagotchi != null) {
            val updatedTamagotchi = Tamagotchi(
                0,
                eat = (currentTamagotchi.eat - 1).coerceAtLeast(0),
                sleep = (currentTamagotchi.sleep - 1).coerceAtLeast(0),
                joy = (currentTamagotchi.joy - 1).coerceAtLeast(0),
                toilet = (currentTamagotchi.toilet - 1).coerceAtLeast(0),
                tennisBall = currentTamagotchi.tennisBall,
                footBall = currentTamagotchi.footBall,
                apple = currentTamagotchi.apple,
                broccoli = currentTamagotchi.broccoli,
                peas = currentTamagotchi.peas,
                strawberry = currentTamagotchi.strawberry,
                pomegrenade = currentTamagotchi.pomegrenade,
                cucumber = currentTamagotchi.cucumber,
                kiwi = currentTamagotchi.kiwi,
                salat = currentTamagotchi.salat,
                toiletPaper = currentTamagotchi.toiletPaper
            )

            repo.updateTamagotchiStats(updatedTamagotchi)
        }
    }
}
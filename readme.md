## ViewModel-View Communication

##### Универсальное решение для взаимодействия ViewModel с View. Решение включает в себя навигацию из ViewModel и передачу данных от ViewModel к View.

------------


#### Навигация [GitHub](https://github.com/Sokolov-Dmitriy/MVVMCommunication/tree/master/app/src/main/java/com/sokolovds/mvvmcommunication/presentation/utils/navigationHandler "GitHub"):
##### Навигация использует [Navigation Component](https://developer.android.com/guide/navigation "Navigation Component"), передавая события через [SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/ "SharedFlow").
##### Пример использования:
```java
class MyViewModel(
    private val navigationController: NavigationController
) : ViewModel() {
    //получаем ссылку на SharedFlow и передаем scope, привязанный к жц
    val navActionFlow = navigationController.navActionFlow(viewModelScope)

    fun onBackPressed() {
		//вызываем popBackStack()
        navigationController.back()
    }

	fun onNextPage(){
		//вызываем navigate()
		navigationController.navigateTo(NavDirections)
	}
}
```
```java
class MyFragment : Fragment(R.layout.fragment) {
    private val binding by ...
    private val viewModel by ...
	//в примере используется inject через koin, необходимо передать lifecycleScope, полученный ранее SharedFlow от NaviagtionController и NavController.
    private val navigationHandler by inject<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) {
        parametersOf(
            lifecycleScope,
            viewModel.navActionFlow,
            findNavController()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationHandler.subscribe()
        binding.backBtn.setOnClickListener {
            viewModel.onBackPressed()
        }
		binding.nextBtn.setOnClickListener {
            viewModel.onNextPage()
        }
    }
}
```

------------


#### Передача данных [GitHub](https://github.com/Sokolov-Dmitriy/MVVMCommunication/tree/master/app/src/main/java/com/sokolovds/mvvmcommunication/presentation/utils/stateHandler "GitHub"):
##### При передаче данных используется [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html "StateFlow"), поэтому передавать scope не нужно. Для сущностей на Domain уровне необходимо реализовать интерфейс MapperToUI, пример:
```java
data class HumanEntity(
    val name: String,
    val age: Int
) : MapperToUI<HumanUIEntity>{
   
    override fun toUIEntity(): HumanUIEntity =
        HumanUIEntity(
            name = name,
            age = age
        )
}
```
##### Пример использования:
```java
class MyViewModel(
    private val getRandomHuman: GetRandomHuman
) : ViewModel() {

	//в данном случае я создаю контроллер сразу в UseCase
    private val randomHumanController = getRandomHuman.controller
	//получаем StateFlow из контроллера
    val randomHumanState = randomHumanController.stateFlow

    fun loadRandomHuman() {
        viewModelScope.launch {
			//событие загрузки
            randomHumanController.loadingState()
            getRandomHuman()
				//событие успешного окончания загрузки
                .onSuccess { randomHumanController.successState(it) }
        }
    }
}
```
```java
class MyFragment : Fragment(R.layout.first_fragment) {
    private val binding by ...
    private val viewModel by ...

	//также, как и в навигации, передается lifecycleScope, 
	//StateFlow, полученный из контроллера 
	//и объект HandlerImplementation, в котором необходимо 
	//реализовать 4 колбек функции: 
	//      - setupStartConfiguration - вызывается в самом начале всегда, 
	//это дефолтное значение для StateFlow, я обычно инициализирую в нем списки,
	//адаптеры, вызываю загрузку и т.д.
	//      - onSuccessState - вызывается, если вызвать successState у контроллера
	//      - onErrorState - вызывается, если вызвать errorState у контроллера
	//      - onLoadingState - вызывается, если вызвать loadingState у контроллера
    private val randomHumanHandler by inject<ViewHandler>(named(ViewHandlerEnum.RANDOM_HUMAN)) {
        parametersOf(
            lifecycleScope,
            viewModel.randomHumanState,
            object : StateHandler.HandlerImplementation<HumanUIEntity> {
                override fun onSuccessState(data: HumanUIEntity) {
                    binding.textSuspendFromRepository.text = data.name
                    showMainContent()
                }

                override fun onErrorState(error: ApplicationError) {}

                override fun onLoadingState() = showProgressBar()

                override fun setupStartConfiguration() {
                    viewModel.loadRandomHuman()
                }
            }
        )
    }
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomHumanHandler.subscribe()
    }
	private fun showProgressBar() {...}

    private fun showMainContent() {...}
}
```

------------
##### Разработчик:
##### - [Соколов Дмитрий](https://github.com/Sokolov-Dmitriy "Соколов Дмитрий")


## Bliss Recruitment app


### How to run:

#### Method 1:

* Import the project to Android Studio
* Install all the asked dependecies
* Clean and Rebuild the project (to ensure that everything is ok)
* Run the project on your device of choice


#### Method 2:

* Download and install the apk available on the following last release.



### Dependencies used:

* Okhttp (https://github.com/square/okhttp)
* Okhttp-logging-interceptor (https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
* Retrofit (https://github.com/square/retrofit)
* Gson (https://github.com/google/gson)
* Rxjava (https://github.com/ReactiveX/RxJava) 
* RxAndroid (https://github.com/ReactiveX/RxAndroid)
* Picasso (https://github.com/square/picasso)
* Dagger (https://github.com/google/dagger)



### Useful commands for testing application deep-links: 

* **Question list screen:**
	* **Without search:** 
		* `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions" bliss.blissrecruitmentapp`
	* **With search:**	
		* `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions?question_filter={search filter}" bliss.blissrecruitmentapp`

* **Question details screen:**
    *  `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions?question_id={question id}" bliss.blissrecruitmentapp`
	
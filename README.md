
## Bliss Recruitment app


### How to run:

#### Method 1:

* Import the project to Android Studio;
* Install all the asked dependecies;
* Clean and Rebuild the project (to ensure that everything is ok);
* Run the project on your device of choice;


#### Method 2:

* Download and install the apk available on the following url:
    * [ADD LINK]



### Dependencies used:

* Okhttp
* Okhttp-logging-interceptor
* Retrofit
* Gson
* Rxjava and RxAndroid
* Picasso
* Dagger



### Useful commands for testing application deep-links: 

* **Question list screen:**
	* **Without search:** 
		* `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions" bliss.blissrecruitmentapp`
	* **With search:**	
		* `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions?question_filter={search filter}" bliss.blissrecruitmentapp`

* **Question details screen:**
    *  `adb shell am start -W -a android.intent.action.VIEW -d "blissrecruitment://questions?question_id={question id}" bliss.blissrecruitmentapp`
	
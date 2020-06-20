# CleanArchitecture

Kotlin android application example with MVVM pattern, android architecture components, and kotlin coroutine.

Screenshot
-----------

![Demo screenshot](screenshot/video.gif "gif demo")


Clean Architecture
-----------

![graph](https://github.com/webaddicted/CleanArchitecture/blob/master/screenshot/clean.jpg)



# Release 2.0 (09/FEB/2020)

https://github.com/webaddicted/CleanArchitecture/archive/2.0.zip

This release cover following feature :

1) Clean Architecture
2) Modular coding
3) Simplified api calling using coroutine
4) MVVM (MODEL - VIEW - VIEWMODEL)
5) Koin
6) Room DB


# Release 1.0 (09/FEB/2020)

https://github.com/webaddicted/CleanArchitecture/archive/1.0.zip

This release cover following feature :

1) Clean Architecture
2) Modular coding
3) Layering on api calling
4) MVVM (MODEL - VIEW - VIEWMODEL)
5) Complex Kotlin Coroutines
6) Koin
7) Room DB

MVVM Architecture
-----------

![graph](https://github.com/webaddicted/CleanArchitecture/blob/master/screenshot/final_architecture.png)

<b> LiveData </b>
is basically a data holder and it is used to observe the changes of a particular view and then update the corresponding change. What is the difference between livedata and mutablelivedata? -> LiveData is immutable while MutableLiveData is mutable.


<b> Dependency Injection </b>
It is a design pattern. we should not create instance of other CLASS(B) inside a CLASS(A) bec it is making CLASS(A) dependant on CLASS(B). it makes our code tightly coupled. For example we have class driver and class vehicle. class driver is dependent on class vehicle.

<b> Coroutines </b>

<b> When do you need background thread? </b>
1- Network Request like retrofit becuse you cant block main thread.
2- Room db/ internal request to db

When you do a network operation, you cant handle network call in the main thread. you need background thread to handle the operation. So getting the result from background thread  and then take the result and display it in main thread.

Another option for background threading in android is coroutines . Co - cooperation, routines mean function so that means function cooperating  with each other. It is a new way of writing asynchronous code which is light weight and much more efficient background thread. 




## LICENSE
```
MIT License

Copyright (c) 2020 Deepak Sharma (webaddicted)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

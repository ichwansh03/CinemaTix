# Cinema App

Cinema App is a a mobile-based application for ordering movie tickets with the application of an API and SQL database with various features, one of which is ordering seats for the reservation. You can get this source code free with follow this step:
* clone this repository:
  ```
  git clone https://github.com/ichwansh03/MovieCompfest.git
  ```
* open clone result in `Android Studio`.
* because this project is open-based API, ![download](https://github.com/ichwansh03/MovieCompfest-Backend) or clone code from here:
  ```
  git clone https://github.com/ichwansh03/MovieCompfest-Backend.git
  ```
* after clone back-end project, you must be import `dbcinema.sql` script from back-end project with `SQLyog` or whatever.
* if you use localhost, save project in `htdocs` file inside `xampp` folder.
* before your build or run this project, please change IP to access localhost in ![BASE_URL](https://github.com/ichwansh03/MovieCompfest/blob/master/app/src/main/java/com/ichwan/moviecompfest/service/GlobalData.kt). Change `BASE_URL` value to `http://<your IP local>/cinema`.
* and then, run or build your project.
 
![ss-movie1](https://github.com/ichwansh03/MovieCompfest/assets/34907490/578e1add-a6c6-44ab-98ed-04a2727173b5)

![ss-movie2](https://github.com/ichwansh03/MovieCompfest/assets/34907490/4f44fcd8-deaa-4203-acd2-5695e69e659a)

MIT License

Copyright (c) 2023 Ichwan Sholihin

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

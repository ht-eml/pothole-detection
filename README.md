# Abstract
Poor road conditions are causing a lot of discomfort to people, leading to vehicle damage and loss of precious lives. Moreover, potholes lead to the D-shaping of the tyre, making them weak and in need of replacement sooner rather than later. Not changing the weak tyre can cause tyre burst, leading to road accidents and loss of precious lives. Potholes also cause great damage to the car’s suspension, bolts and shocks, causing them to be changed once every six months.

# Pothole-Detection-App
An Android application built using Artificial Intelligence techniques
Application detects the potholes on roads and after detecting marks the location of potholes on google map.
Detection is done using two techniques 
1. Vision based,
   A deep learning model Mobilenet ssd v2 quantized model was trained on our self collected dataset of potholes and was deployed to android app to do inferencing/prediction using mobile phones camera.
2. Sensor Based,
   LSTM model was used to detect potholes using Accelerometer Sensor of mobile phones, model was trained on self collected data and was deployed to android device to do inferencing.   

Locations are then saved on a server developed on ruby on rails having the visual map containing the marked locations of potholes that can be viewed by admin.
   

# Dataset
The dataset used to solve the pothole detection problem was self-designed, claiming that there is no copyright issue. The dataset was collected from Lahore City roads like Valencia, Allama Iqbal town, Zarrar shaheed road, College road, etc.The dataset was labeled using LabelImg tool.
The Sensor-based pothole detection model was trained on the dataset collected using an android application, that, when the user drives a car on the road with the windshield mounted cellphone holder collects the accelerometer (x, y & z axes) data and saves it in a .csv file on the android phone, the dataset was labeled manually in the .csv files. 

# Results
The best-performing methods in the pothole detection problem were SSD MobileNet v2 quantized and LSTM with 0.95 and 0.24 test loss respectively, whereas, the mAP for SSD MobileNet v2 quantized is 81% and test accuracy for LSTM is 96%.


# Screenshots from Application
      Here are some screenshots from the android application.
<p float= "left">
<img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/Picture%202.png" width="200" height="400"/> 
<img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/Picture%204.png" width="200" height="400"/> 
<img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/Picture%203.png" width="200" height="400"/> 
</p>

 Application's main page allows the user to select the desired method to detect the pothole.
 While the Map option shows the map having information of recently marked potholes.

# Web Server (Ruby on Rails)
      Screenshot of web server showing the marked pothole locations from the application.
<p float= "centre">
<img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/laptop.jpg" width="600" height="400"/> 
</p>
     -Only Admin can access the web server.
 
  # Application detecting potholes using Vision based method
      Screenshot of application detecting potholes using Vision based method.
  
  <p float= "left">
   <img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/p1.JPG" width="200" height="400"/> 
   <img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/p2.JPG" width="200" height="400"/> 
   <img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/p3.JPG" width="200" height="400"/> 
  </p>
 
   Picture 1,2 & 3 are showing the bounding box with probability on detected potholes.
   
   # Application detecting potholes using Sensor based method
      Screenshot of application detecting potholes using Sensor based method.
   
   <p float= "left">
   <img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/pothole.JPG" width="200" height="400"/> 
   <img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/smooth.JPG" width="200" height="400"/> 
  </p>
   Picture 1 shows the pothole detected with the probability and similarly picture 2 shows the probabilty that car is going on smooth road.

# Application Procedure
<p float= "centre">
<img src="https://github.com/hamzatanvir/pothole-detection/blob/master/pictures/Screenshot%202020-05-22%20at%209.35.32%20PM.png" width="1060" height="600"/> 
</p>

  # This repository contains
      1. Android App apk 
      2. Android App code
      3. Web server code
      4. Vision based and Sensor Based model files.
      
  # Our Dataset is not available Publicly.





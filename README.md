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
   
This repository contains the final apk file along with the android application code, rails sever code and machine learning model files.

# Dataset
The dataset used to solve the pothole detection problem was self-designed, claiming that there is no copyright issue. The dataset was collected from Lahore City roads like Valencia, Allama Iqbal town, Zarrar shaheed road, College road, etc.The dataset was labeled using LabelImg tool.
The Sensor-based pothole detection model was trained on the dataset collected using an android application, that, when the user drives a car on the road with the windshield mounted cellphone holder collects the accelerometer (x, y & z axes) data and saves it in a .csv file on the android phone, the dataset was labeled manually in the .csv files. 

# Results
The best-performing methods in the pothole detection problem were SSD MobileNet v2 quantized and LSTM with 0.95 and 0.24 test loss respectively, whereas, the mAP for SSD MobileNet v2 quantized is 81% and test accuracy for LSTM is 96%.

# Our Dataset is not available publicly.

# Application 
Here are some screenshots of the pothole detector application

![Main Page](/pictures/Picture 1.png)



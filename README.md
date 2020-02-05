# Pothole-Detection-App
An Android application built using Artificial Intelligence techniques
Application detects the potholes on roads and after detecting marks the location of potholes on google map.
Detection is done using two techniques 
1. Vision based,
   A deep learning model Mobilenet ssd v2 quantized model was trained on our self collected dataset of potholes and was deployed to android    app to do inferencing/prediction using mobile phones camera.
2. Sensor Based,
   LSTM model was used to detect potholes using Accelerometer Sensor of mobile phones, model was trained on self collected data and was        deployed to android device to do inferencing.   

Locations are then saved on a server developed on ruby on rails having the visual map containing the marked locations of potholes that can be viewed by admin.
   
This repository contains the final apk file along with the android application code and machine learning model files.
# Our Dataset is not available publicly.

# ML-DiagnosisPrediction
As a project for COEN352, I built a Machine Learing model with Java to predict the diagnosis of cancer cells. The implementation resulted in an average diagnostic accuracy of 85-100%. The report for the project, which includes a full performance analysis, can be found in the repository.

### Objectives
- To create a Machine Learning model for predicting the diagnosis of cancer cells with an accuracy higher than 80%
- To test and evaluate teh accuracy of the model
- To test and evaluate the computational efficiency of the model
- To have the model be as scalable as possible

### Data Structures and Algorithms
- K-D tree data structure was used for the organization of the training data.
- k Nearest Neighbour (k-NN) machine learning model was used to predict the diagnosis of the testing data set.

#### K-D Tree structure
The following image shows the base K-D Tree structure for this ML model. It is a full, balanced binary tree which organizes data by comparing different attributes at each depth level.
<img width="689" alt="Screenshot 2025-03-16 at 11 12 57 AM" src="https://github.com/user-attachments/assets/9e9b5579-133d-4621-8bb9-54be77d1b1bb" />

#### K-D Tree building algorithm
The algorithm to build a balanced K-D tree of any dimension is depicted in the flowchart. At each depth, the attribute used for comparison is determined and the data is sorted based on it. Then, the midpoint is found and set as the current Node, and the tree is built recursively to the left and to the right using the corresponding left and right subarrays.
<img width="378" alt="Screenshot 2025-03-16 at 11 16 03 AM" src="https://github.com/user-attachments/assets/c4435ce8-2476-4090-a555-1c421d771356" />

#### Testing K-D Tree with k-NN algorithm
This algorithm tests the accuracy and runtime of predicting diagnoses for the testing list. For every instance in the testing Points array, its k nearest neighbors are found in the K-D tree. Then, through majority voting, a diagnosis (B for benign or M for malignant) is predicted for that Point. Finally, the predicted and actual diagnoses of each Point are compared, and the total number of correct and incorrect predictions is found. The percentage of accuracy is then calculated and output.
<img width="482" alt="Screenshot 2025-03-16 at 11 17 34 AM" src="https://github.com/user-attachments/assets/f84f0c39-0aeb-4298-af52-83b0198352b4" />

### Lessons & Conclusion
This project taught me the basics of Machine Learning with Object-Oriented Programming. Building the data structures and testing algorithms myself gave me an in-depth understanding of how ML models operate at the core. The project met all of its objectives successfully and I really enjoyed completing it!

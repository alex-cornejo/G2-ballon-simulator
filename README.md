### Requirements
* To have Java 8 installed and added to the environment path
* To have Maven installed and added to the environment path

### Run unit tests
`mvn test`

### Excecute program
`mvn clean compile exec:java`

### Details

This tool uses two different graphs to handle the conversions of the units, these graphs graphs assume that obervatory units are the follow:

|	Observatory | Temperature	| Distance	|
|-------------|-------------|-----------|
|  AU	        |celsius	    |km	        |
|  US	        |fahrenheit	  |miles	    |
|  FR	        |kelvin	      |m |1	      |
| All Others  |kelvin	      |km	        |


This graph represents the equivalences between different observatories for distances.

|	    |AU	      |US	         |FR	    |Other  |
|-----|---------|------------|--------|-------|
|  AU	|1	      |0.621371	   |1000	  |1      |
|  US	|1.60934	|1	         |1609.34	|1.60934|
|  FR	|0.001	  |0.000621371 |1	      |0.001  |
|Other|1	      |0.621371	   |1000	  |1      |


This graph contains different functions according to the algorithm necessary to convert from one observatory temperature units to another one.

|	    |AU	     |US	    |FR	     |Other   |
|-----|--------|--------|--------|--------|
|  AU	| T_TO_T | C_TO_F | C_TO_K | C_TO_K |
|  US	| F_TO_C | T_TO_T | F_TO_K | F_TO_K |
|  FR	| K_TO_C | K_TO_F | T_TO_T | T_TO_T |
|Other| K_TO_C | K_TO_F | T_TO_T | T_TO_T |

where:
${A_TO_B} is a function to convert from the unit *A* to the unit *B*, *A* and *B* can take the values:  

C=Celsius  
F=Fahrenheit  
K=Kelvin  
T=Any temperature unit  


### Assumptions of Datasets generation
- The range of random temperatures is the same for all observatories [0, 100]
- The range of random locations is positive and the same for all observatories [0, 10] 
- The range of random timestamps is from 1970 to current date
- Records of dataset are generated unsorted but all of them keep the right format 

NOTE: Previous configs can be changed on class "RecordBallon"

### How it process the datasets
It uses the external merge sort algorithm to sort a large file which does not fit in memory.
External sorting ->  https://en.wikipedia.org/wiki/External_sorting

1. The large dataset is splited in multiple datasets which are called batches.
2. Every batch is sorted in memory and written on disk.
3. Mini batches are loaded in memory and are merged in a output sorteddataset file.
4. Sorted dataset is read from the disk line by line and the requirements are computed (Previous graphs are used in this step to do the conversions between the observatories).
5. Final result is printed and logged. 

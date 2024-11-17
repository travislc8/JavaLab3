Data Visualization tool for OOP with Java class. 
Main driver file is src/DataVisuallizationTool.java

**Updates for Lab 4**
For lab 4, the observer pattern and decorator pattern were added. 
Observer Pattern was added to the TablePanel class. Befor the a seperate object was held for the stats and chart panels. Now there is an array of objects implementing the TablePanelObserver interface.

Decorator pattern was added to the filter options and the drow downdown boxes used in the filter panel. The FilterDecorator class extends the JCheckbox class and the JComboBoxDecorator extends JComboBox<String>.

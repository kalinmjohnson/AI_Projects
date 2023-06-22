module sudoku {
	requires java.desktop;
	requires org.junit.jupiter.api;
	requires javafx.graphics; 
	requires javafx.controls;
	requires javafx.base;
	requires java.management;
	opens sudoku to javafx.graphics;
}
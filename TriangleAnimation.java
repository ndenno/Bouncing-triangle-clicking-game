import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.event.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TriangleAnimation extends Application{
	
	private Pane root;
	//private Triangle triangle;
	private Polygon triangle;
	private TriangleMovement ta;
	private Text stats;
	private Rectangle background;
	private int score = 0;
	private Double[] trianglePoints = new Double[] {100.0,100.0,
													170.0,170.0,
													30.0,170.0} ;
	
	private int xVelocity = 5;
	private int yVelocity = 2;
	private int rotation = -3;
	
	private final int WINDOW_WIDTH = 1080;
	private final int WINDOW_HEIGHT = 720;
	
	
	
	
	@Override
	public void start(Stage stage) {
		stage.setResizable(false);
		root = new Pane();
		
		//objects
		background = new Rectangle(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
		background.setFill(Color.BLACK);
		background.setOnMouseClicked(new ClickListener());
		
		Rectangle box = new Rectangle(300,300,100,100);
		box.setFill(Color.ANTIQUEWHITE);
		
		triangle = new Polygon();
		triangle.getPoints().addAll(trianglePoints);
		triangle.setFill(Color.RED);
		triangle.setStroke(Color.GREEN);
		triangle.setOnMouseClicked(new ClickListener());
		
		stats = new Text("Score: " + score);
		stats.setLayoutX(10);
		stats.setLayoutY(WINDOW_HEIGHT-20);
		stats.setFill(Color.WHITE);
		stats.setStyle("-fx-font-size: 30");
		stats.setMouseTransparent(true);
		
		
		//Triangle triangle = new Triangle(100,100,100,100);
		
		ta = new TriangleMovement();
		ta.start();
		
		root.getChildren().addAll(background, triangle, stats);
		Scene scene = new Scene(root,WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setTitle("Triangle Game");
		stage.setScene(scene);
		stage.show();
	
	}
	
	private class ClickListener implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			
			if (score > -11 && score < 11) {
				if (e.getSource() != triangle) {
					score--;
					e.consume();
				}
				else {
					score++;
					e.consume();
				}
				stats.setText("Score: " + score);
			}
			
			
		}
	}
	
	//unused triangle object
	private class Triangle extends Group{
		
		public Triangle(int x, int y,int w, int h) {
			Polygon tr = new Polygon();
			Double[] trianglePoints = new Double[] {(double)WINDOW_WIDTH/2,(double)WINDOW_HEIGHT/2,
													(double)WINDOW_WIDTH/2 + w,(double)WINDOW_HEIGHT/2 + h,
													(double)WINDOW_WIDTH/2 - w,(double)WINDOW_HEIGHT/2 + h} ;
			
			tr.getPoints().addAll(trianglePoints);
			tr.setFill(Color.RED);
			tr.setStroke(Color.GREEN);
			tr.setLayoutX(x);
			tr.setLayoutY(y);
			
			this.getChildren().add(tr);
			
			
		}
	}
	
	private class TriangleMovement extends AnimationTimer{
		
		int counter = 0;
		@Override
		public void handle(long now) {
			counter ++;
			
			
			double x = triangle.getLayoutX();
			double y = triangle.getLayoutY();
			double r = triangle.getRotate();
			
			
			
			if(x+xVelocity > WINDOW_WIDTH-100 || x+xVelocity < -100) {
				xVelocity *= -1;
				rotation *= -1;
				//System.out.println("OOB");
			}
			
			 if(y+yVelocity> WINDOW_HEIGHT-100 || y+yVelocity< -100) {
				yVelocity *= -1;
				rotation *= -1;
				//System.out.println("OOB");
			}
			 
			x+=xVelocity;
			y+=yVelocity;
			r+= rotation;
			
			triangle.setLayoutX(x);
			triangle.setLayoutY(y);
			triangle.setRotate(r);
			
			 if (counter ==60) {
				 System.out.println("X= " + triangle.getLayoutX());
				 System.out.println("Y = " + triangle.getLayoutY());
				 counter = 0;
			 }
			 
			 if (score <-10) {
					stats.setText("Sorry, you ran out of clicks");
					ta.stop();
					root.getChildren().removeAll(triangle);
				}
				
				else if (score > 10) {
					stats.setText("Congratulations! You won the game");
					root.getChildren().removeAll(triangle);
					ta.stop();
				}
			
		}
	}
	
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	
	
	
}
import java.util.*;

public class Kmeans {
	private ArrayList<Car> cars = new ArrayList<Car>();
	private double[][] centroids = {{0,0,0,0,0,0}, {1,1,1,1,1,1}, {2,2,2,2,2,2}};
	private double[][] lastCentroids = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};;
	private ArrayList<ArrayList<Car>> clusters = new ArrayList<ArrayList<Car>>();

	public Kmeans() {
		this.cars = (new DatasetCar()).getCars();
		this.initClustering();
	}

	public Kmeans(DatasetCar data) {
		this.cars = data.getCars();
		this.initClustering();
	}

	public void initClustering() {
		for(int i=0; i<3; i++)
			this.clusters.add(i, new ArrayList<Car>());

		this.logCentroidsAndLast();
		while(this.clusterizePoints() != 0){
			this.logCentroidsAndLast();
		}

		this.logCentroids();
		System.out.println("Ok!");
	}

	public void logCentroids() {
		System.out.println("Centroids:");
		for(int i=0; i<3; i++) {
			System.out.printf(java.util.Locale.US,"{%.2f", this.centroids[0][0]);
			for(int j=1; j<6; j++) System.out.printf(java.util.Locale.US, ", %.2f", this.centroids[i][j]);
			System.out.println("}");
		}
	}

	public void logCentroidsAndLast() {
		System.out.println("Last Centroids:");
		for(int i=0; i<3; i++) {
			System.out.printf(java.util.Locale.US,"{%.2f", this.centroids[0][0]);
			for(int j=1; j<6; j++) System.out.printf(java.util.Locale.US, ", %.2f", this.centroids[i][j]);
			System.out.println("}");
		}

		this.logCentroids();
	}

	private boolean isFirstClustering() {
		int length = 0;
		for(int i=0; i<3; i++) {
			length += this.clusters.get(i).size();
		}

		return length <= 0;
	}

	private boolean clustersAreEquals() {
		for(int i=0; i < this.centroids.length; i++) {
			for(int j=0; j<6; j++) {
				if(this.centroids[i][j] != this.lastCentroids[i][j])
					return false;
			}
		}

		return true;
	}

	private void copyClusterToLast() {
		for(int i=0; i<this.centroids.length; i++){
			this.lastCentroids[i] = this.centroids[i];
		}
	}

	private int clusterizePoints() {
		if(clustersAreEquals()) {
			return 0;
		}
		
		double dist = 0;
		double minorDist = -1;
		int index = 0;
		this.copyClusterToLast();

		for(Car car : this.cars) {
			for(int i=0; i<this.centroids.length; i++) {
				double[] vector = car.getAttributesDouble();
				// double[] v = vector;
				// System.out.println("V: " + v[0] +" - "+ v[1] +" - "+ v[2] +" - "+ v[3] +" - "+ v[4] +" - "+ v[5]);

				dist = this.getDistance(vector, this.centroids[i]);
				if(dist < minorDist || minorDist == -1) {
					minorDist = dist;
					index = i;
				}
			}

			this.clusters.get(index).add(car);
		}

		double[][] avarage = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		for(int i=0; i<3; i++) {
			for(Car car : this.clusters.get(i)) {
				double[] vector = car.getAttributesDouble();
				// double[] v = vector;
				// System.out.println("V: " + v[0] +" - "+ v[1] +" - "+ v[2] +" - "+ v[3] +" - "+ v[4] +" - "+ v[5]);
				for(int j=0; j < vector.length; j++) {
					avarage[i][j] += vector[j];
				}

				for(int j=0; j < vector.length; j++) {
					avarage[i][j] += vector[j];
				}
			}

			if(this.clusters.get(i).size() <= 0) {
				System.out.println("\nTAMANHO 00000!!!!\n");
			}

			System.out.println("\n\nTeste: ");
			for(int j=0; j < 6; j++) {
				avarage[i][j] /= this.clusters.get(i).size();
				System.out.print("- " + avarage[i][j]);
			}
			System.out.println("\n\n");

			centroids[i] = avarage[i];
		}

		return -1;
	}

	private double getDistance(double[] vetA, double[] vetB) {
		try {
			if(vetA.length == vetB.length) {
				double diff = 0;
				double sum = 0;
				for(int i=0; i<vetA.length; i++) {
					diff = Math.abs(vetA[i]-vetB[i]);
					sum += Math.pow(diff, new Double("2"));
				}

				return Math.sqrt(sum);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public static void main(String[] args) {
		new Kmeans();		
	}
}

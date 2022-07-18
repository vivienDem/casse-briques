package interfaces;

public interface DataService extends ReadService, WriteService {
	public void init(String filename);
}

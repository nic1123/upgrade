package upgrade.nicky.reservation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import upgrade.nicky.reservation.model.Reservation;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationApplicationTests {

	private ExecutorService executorService;
	private final LocalDate tomorrow = LocalDate.now().plusDays(1);

	@LocalServerPort
	private int port;

	@Before
	public void init() {
		executorService = Executors.newFixedThreadPool(5);

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	@Transactional
	public void testConcurrentRequests() throws InterruptedException, ExecutionException {

		TestRestTemplate restTemplate = new TestRestTemplate();

		Set<Callable<ResponseEntity<String>>> callables = new HashSet<Callable<ResponseEntity<String>>>();

		for (int i = 0 ; i < 5; i++) {
			callables.add(new Callable<ResponseEntity<String>>() {
				@Override
				public ResponseEntity<String> call() throws Exception {
					Reservation reservation = new Reservation("Nicky", "Siu", "test@gmail.com", tomorrow, tomorrow.plusDays(2));
					ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/campsite/reservation"), reservation, String.class);
					return response;
				}


			});

		}

		List<Future<ResponseEntity<String>>> futures = executorService.invokeAll(callables);

		//check that there should be 1 request with status = OK ad 4 requests with status = BAD_REQUEST
		int ok_status = 0;
		int bad_request_status = 0;
		for(Future<ResponseEntity<String>> future : futures){
			if (((ResponseEntity<String>) future.get()).getStatusCode().equals(HttpStatus.OK))
				ok_status++;
			else if ((((ResponseEntity<String>) future.get()).getStatusCode().equals(HttpStatus.BAD_REQUEST)))
				bad_request_status++;
		}


		assertEquals(ok_status, 1);
		assertEquals(bad_request_status, 4);

		executorService.shutdown();

	}



}

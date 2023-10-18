package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
	// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	//S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void noTicketPoorMoney(){
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(), "Impression impossible");
	}

	@Test
	//S4 : on imprime le ticket si le montant inséré est suffisant
	void ticketEnoughMoney(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Impression possible");
	}

	@Test
	//S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void updateBalanceWhenPrintTicket(){
		machine.insertMoney(30);
		machine.insertMoney(30);
		machine.printTicket();
		assertEquals(30+30-PRICE, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	//S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void updateTotalWhenPrintTicket(){
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le montant collecté n'est pas correctement mis à jour");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundCorrectly(){
		machine.insertMoney(PRICE+10);
		machine.printTicket();
		machine.refund();
		assertEquals(10, machine.getBalance()," La monnaie n'est pas bien rendue");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void balanceNullWhenRefund(){
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'est pas remise à zéro");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void noInsertNegativePrice(){
		//assertThrows(IllegalArgumentException.class, () -> {machine.insertMoney(-10);}, "Le montant inséré ne peut pas être négatif");
		try{
			machine.insertMoney(-10);
			fail("Le montant inséré ne peut pas être négatif");
		} catch (IllegalArgumentException e){

		}
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void noTicketNegativePrice(){
		//TicketMachine machine = new TicketMachine(-10);
		//assertThrows(IllegalArgumentException.class, () -> {machine.getPrice();}, "Le prix du ticket ne peut pas être négatif");
		try{
			TicketMachine machine = new TicketMachine(-10);
			fail("Le prix du ticket ne peut pas être négatif");
		} catch (IllegalArgumentException e){

		}
	}
}

package poker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import exceptions.DeckException;
import netgame.common.Hub;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.CardDraw;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerBase.Table;
import pokerEnums.eAction;
import pokerEnums.eCardDestination;
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eGameState;

public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;
	//private PokerGameState state;
	private eGameState eGameState;

	public PokerHub(int port) throws IOException {
		super(port);
	}

	protected void playerConnected(int playerID) {

		if (playerID == 2) {
			shutdownServerSocket();
		}
	}

	protected void playerDisconnected(int playerID) {
		shutDownHub();
	}

	protected void messageReceived(int ClientID, Object message) {

		if (message instanceof Action) {
			Action act = (Action) message;
			switch (act.getAction()) {
			case GameState:
				sendToAll(HubPokerTable);
				break;
			case TableState:
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case Sit:
				resetOutput();
				HubPokerTable.AddPlayerToTable(act.getPlayer());				
				sendToAll(HubPokerTable);				
				break;
			case Leave:
				resetOutput();
				HubPokerTable.RemovePlayerFromTable(act.getPlayer());
				sendToAll(HubPokerTable);				
				break;
				
			case StartGame:
				System.out.println("Starting Game!");
				//TODO - Lab #5 Do all the things you need to do to start a game!!
				
				//	Determine which game is selected (from RootTableController)
				//		1 line of code
				eGame gameSelected = act.geteGame();
				
				//	Get the Rule based on the game selected
				//		1 line of code
				
				Rule rle = new Rule(act.geteGame());
				
				//	The table should eventually allow multiple instances of 'GamePlay'...
				//		Each game played is an instance of 'GamePlay'...
				//		For the first instance of GamePlay, pick a random player to be the 
				//		'Dealer'...  
				//		< 5 lines of code to pick random player
				
				Player p = HubPokerTable.PickRandomPlayerAtTable();
				System.out.println("Random Player: " + p.getiPlayerPosition());
				
				//	Start a new instance of GamePlay, based on rule set and Dealer (Player.PlayerID)
				//		1 line of code
				
				HubGamePlay = new GamePlay(rle, p);
				
				//	There are 1+ players seated at the table... add these players to the game
				//		< 5 lines of code
				
				HubGamePlay.setGamePlayers((HubPokerTable.getHashPlayers()));
				
				//	GamePlay has a deck...  create the deck based on the game's rules (the rule
				//		will have number of jokers... wild cards...
				//		1 line of code

				HubGamePlay.setGameDeck(new Deck(rle.GetNumberOfJokers(), rle.GetRuleCards()));
				
				//	Determine the order of players and add each player in turn to GamePlay.lnkPlayerOrder
				//	Example... four players playing...  seated in Position 1, 2, 3, 4
				//			Dealer = Position 2
				//			Order should be 3, 4, 1, 2
				//	Example...  three players playing... seated in Position 1, 2, 4
				//			Dealer = Position 4
				//			Order should be 1, 2, 4
				//		< 10 lines of code -- circular linked list
				
				HubGamePlay.setiActOrder(GamePlay.GetOrder(p.getiPlayerPosition()));
				
				//	Set PlayerID_NextToAct in GamePlay (next player after Dealer)
				//		1 line of code
				
				HubGamePlay.setPlayerNextToAct(HubGamePlay.ComputePlayerNextToAct(p.getiPlayerPosition()));
				
				System.out.println("Dealer: " + p.getPlayerName());
				System.out.print("Next To Act: " + HubGamePlay.getPlayerNextToAct());
				
				if (p == HubGamePlay.getPlayerNextToAct()) {
					try {
						throw new Exception("Dealer and next to act are same");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// set the draw to the first draw
				HubGamePlay.setDrawCnt(eDrawCount.FIRST);
				
				//try to deal cards
				try {
					DealCards(HubGamePlay.getRule().getCardDraw(HubGamePlay.getCardDraw()));
				} catch (DeckException e) {
					e.printStackTrace();
					sendToAll(e);
				}
				
				//	Here's how to get an eNum based on a given value (Merry Christmas):)
				eGame Game = eGame.getGame(1);

				//	Send the state of the game back to the players
				sendToAll(HubGamePlay);
				break;
			case Deal:
				
				/*
				int iCardstoDraw[] = HubGamePlay.getRule().getiCardsToDraw();
				int iDrawCount = iCardstoDraw[iDealNbr];

				for (int i = 0; i<iDrawCount; i++)
				{
					try {
						Card c = HubGamePlay.getGameDeck().Draw();
					} catch (DeckException e) {
						e.printStackTrace();
					}
				}
*/
				break;
			}
		}

		System.out.println("Message Received by Hub");
	}

	
	private void DealCard(CardDraw cd) throws DeckException {
		for (int i = 0; i < cd.getCardCountDrawn().getCardCount(); i++) {
			if (cd.getCardDestination() == eCardDestination.Player)  {
				for (int n : HubGamePlay.getiActOrder()) {
					if ((HubGamePlay.getPlayerByPosition(n) != null)
							&& (HubGamePlay.getPlayerHand(HubGamePlay.getPlayerByPosition(n).getPlayerID()))
									.isFolded() == false) {
						HubGamePlay.getPlayerHand(HubGamePlay.getPlayerByPosition(n).getPlayerID())
								.Draw(HubGamePlay.getGameDeck());
							}
			}
		}
	}
	if (cd.getCardDestination() == eCardDestination.Community) {
		HubGamePlay.getCommonHand().Draw(HubGamePlay.getGameDeck());
	}
}
}
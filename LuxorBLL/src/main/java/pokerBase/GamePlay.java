package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import javassist.bytecode.Descriptor.Iterator;
import pokerEnums.eDrawCount;

public class GamePlay implements Serializable   {

	private UUID GameID;
	private LinkedList lnkPlayerOrder = new LinkedList<UUID>();	
	private UUID PlayerID_NextToAct = null;
	private HashMap<UUID, Player> hmGamePlayers = new HashMap<UUID, Player>();
	private HashMap<UUID, Hand> hmGamePlayerHand = new HashMap<UUID, Hand>();
	private ArrayList<GamePlayPlayerHand> GamePlayerHand = new ArrayList<GamePlayPlayerHand>();
	private ArrayList<GamePlayPlayerHand> GameCommonHand = new ArrayList<GamePlayPlayerHand>();
	private Rule rle;
	private Deck GameDeck = null;
	private UUID GameDealer = null;
	private Hand CommonHand = new Hand();
	private Player GameDealer1 = null;
	private Player PlayerNextToAct = null;
	private int[] iActOrder = null;
	private eDrawCount DrawCnt;

	
	
	public GamePlay(Rule rle, Player GameDealer) {
		this.setGameID(UUID.randomUUID());
		this.setGameDealer1(GameDealer);
		this.rle = rle;
	}
	//public GamePlay(Rule rle, UUID GameDealerID)
	//{
	//	this.setGameID(UUID.randomUUID());
	//	this.setGameDealer(GameDealer);
	//	this.rle = rle;
	//}

	public UUID getGameID() {
		return GameID;
	}

	public void setGameID(UUID gameID) {
		GameID = gameID;
	}

	public Rule getRule()
	{
		return this.rle;
	}
	
	public HashMap<UUID, Player> getGamePlayers() {
		return hmGamePlayers;
	}

	public void setGamePlayers(HashMap<UUID, Player> gamePlayers) {
		this.hmGamePlayers = new HashMap<UUID, Player>(gamePlayers);
		
	//	Iterator it = getGamePlayers().entrySet().iterator();
	//	while (it.hasNext()) {
	//		Map.Entry pair = (Map.Entry) it.next();
	//		Player p = (Player)pair.getValue();
	//		this.addPlayerHand(p.getPlayerID());
	//	}
	}
	
	public void addPlayerToGame(Player p)
	{
		this.hmGamePlayers.put(p.getPlayerID(),p);
	}
	public Player getGamePlayer(UUID PlayerID)
	{
		return (Player) this.hmGamePlayers.get(PlayerID);
	}

	public Deck getGameDeck() {
		return GameDeck;
	}

	public void setGameDeck(Deck gameDeck) {
		GameDeck = gameDeck;
	}
	
	//public UUID getGameDealer() {
	//	return GameDealer;
	//}
	
	public Player getGameDealer() {
		return GameDealer1;
	}

	private void setGameDealer(UUID gameDealer) {
		GameDealer = gameDealer;
	}

	public void addGamePlayPlayerHand(GamePlayPlayerHand GPPH)
	{
		GamePlayerHand.add(GPPH);
	}
	
	public void addGamePlayCommonHand(GamePlayPlayerHand GPCH)
	{
		GameCommonHand.add(GPCH);
	}

	public void addLnkPlayerOrder(UUID PlayerID)
	{
		lnkPlayerOrder.add(PlayerID);
	}
	public LinkedList getLnkPlayerOrder() {
		return lnkPlayerOrder;
	}

	public UUID getPlayerID_NextToAct() {
		return PlayerID_NextToAct;
	}

	public void setPlayerID_NextToAct(UUID playerID_NextToAct) {
		PlayerID_NextToAct = playerID_NextToAct;
	}
	
	public int[] iActOrder() {
		return iActOrder;
	}
	
	public void setiActOrder(int[] iActOdrer) {
		this.iActOrder = iActOrder;
	}
	
	public eDrawCount getDrawCnt() {
		return DrawCnt;
	}
	
	public void setDrawCnt(eDrawCount nextDraw) {
		DrawCnt = nextDraw;
	}
	
	//
	
	public Player getPlayerByPosition(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<UUID, Player> getHmGamePlayers() {
		return hmGamePlayers;
	}

	public void setHmGamePlayers(HashMap<UUID, Player> hmGamePlayers) {
		this.hmGamePlayers = hmGamePlayers;
	}

	public HashMap<UUID, Hand> getHmGamePlayerHand() {
		return hmGamePlayerHand;
	}

	public void setHmGamePlayerHand(HashMap<UUID, Hand> hmGamePlayerHand) {
		this.hmGamePlayerHand = hmGamePlayerHand;
	}

	public ArrayList<GamePlayPlayerHand> getGamePlayerHand() {
		return GamePlayerHand;
	}

	public void setGamePlayerHand(ArrayList<GamePlayPlayerHand> gamePlayerHand) {
		GamePlayerHand = gamePlayerHand;
	}

	public ArrayList<GamePlayPlayerHand> getGameCommonHand() {
		return GameCommonHand;
	}

	public void setGameCommonHand(ArrayList<GamePlayPlayerHand> gameCommonHand) {
		GameCommonHand = gameCommonHand;
	}

	public Rule getRle() {
		return rle;
	}

	public void setRle(Rule rle) {
		this.rle = rle;
	}

	public Hand getCommonHand() {
		return CommonHand;
	}

	public void setCommonHand(Hand commonHand) {
		CommonHand = commonHand;
	}

	public Player getGameDealer1() {
		return GameDealer1;
	}

	public void setGameDealer1(Player gameDealer1) {
		GameDealer1 = gameDealer1;
	}

	public Player getPlayerNextToAct() {
		return PlayerNextToAct;
	}

	public void setPlayerNextToAct(Player playerNextToAct) {
		PlayerNextToAct = playerNextToAct;
	}

	public int[] getiActOrder() {
		return iActOrder;
	}

	public void setLnkPlayerOrder(LinkedList lnkPlayerOrder) {
		this.lnkPlayerOrder = lnkPlayerOrder;
	}
	
	public Object getCardDraw() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*

	int iNextPosition = 1,
			try { for (int i : iOrder) {
				if (iCurrentPosition = i) {
					iNextPosition = iOrder[i + 1];
				}
			}
			} catch (ArrayIndexOutOfBoundsException e) {
				iNextPosition = iOrder[0];
			}
	if (getPlayerByPosition(iNextPosition) == null) {
		return ComputePlayerNextToAct(iNextPosition,)
	else {
	return getPlayerPosition(iNextPosition);	
	}
 	*/
	
	public Player ComputePlayerNextToAct(int iCurrentPosition) {
		int[] iNextOrder = GetOrder(iCurrentPosition);
		for (int i : iNextOrder) {
			if (getPlayerByPosition(i) != null) {
				if (i == iCurrentPosition)
					return null;
				else
					return getPlayerByPosition(i);
			}
		}
		
		return null;
	}
	
	
	public static int[] GetOrder(int iStartPosition) {
		int[] iPos = null;
		switch (iStartPosition) {
		case 1:
			int[] iPositions1 = new int[] {2, 3, 4, 1};
			iPos = iPositions1;
			break;
		case 2:
			int[] iPositions2 = new int[] {3, 4, 1, 2};
			iPos = iPositions2;
			break;
		case 3:
			int[] iPositions3 = new int[] {4, 1, 2, 3};
			iPos = iPositions3;
			break;
		case 4: 
			int[] iPositions4 = new int[] {1, 2, 3, 4};
			iPos = iPositions4;
			break;
		}
		return iPos;
	}


	
	/*
	public GamePlayPlayerHand FindCommonHand(GamePlay gme)
	{
		GamePlayPlayerHand GPCH = null;
		for (GamePlayPlayerHand GPPH: GameCommonHand)
		{
			if (GPPH.getGame().getGameID() == gme.getGameID())
			{
				GPCH = GPPH;
			}
		}		
		return GPCH;
	}
	*/
	
/*	public GamePlayPlayerHand FindPlayerGame(GamePlay gme, Player p)
	{
		GamePlayPlayerHand GPPHReturn = null;
		
	
		for (GamePlayPlayerHand GPPH: GamePlayerHand)
		{
			if (p.getiPlayerPosition() == GPPH.getPlayer().getiPlayerPosition())
			{
				GPPHReturn = GPPH;
			}
		}
		return GPPHReturn;
	}*/
	
	
}

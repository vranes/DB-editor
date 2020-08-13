package actions;

public class ActionManager {
	private static ActionManager instance = null;
	
	private DoubleClickAction doubleClickAction;
	private UpperPaneChangeListener upperPaneChangeListener;
	private LowerPaneChangeListener lowerPaneChangeListener;
	private AddButtonAction addButtonAction; 
	private RefreshAction refreshAction;
	private UpdateButtonAction updateButtonAction; 
	private DeleteButtonAction deleteButtonAction;
	private SearchButtonListener searchButtonListener;
	private ReportButtonListener reportButtonListener;
	private SortFilterAction sortFilterAction;
	private RelationJoinAction relationJoinAction;
	
	private ActionManager() {
		initializeActions();
	}
	
	private void initializeActions() {
		doubleClickAction = new DoubleClickAction();
		upperPaneChangeListener = new UpperPaneChangeListener();
		lowerPaneChangeListener = new LowerPaneChangeListener();
		addButtonAction = new AddButtonAction();
		refreshAction = new RefreshAction();
		updateButtonAction = new UpdateButtonAction();
		deleteButtonAction = new DeleteButtonAction();
		searchButtonListener = new SearchButtonListener();
		reportButtonListener = new ReportButtonListener();
		sortFilterAction = new SortFilterAction();
		relationJoinAction = new  RelationJoinAction();
	}
	
	public static ActionManager getInstance() {
		if (instance == null) {
			instance = new ActionManager();
		}
		return instance;
	}
	
	public RelationJoinAction getRelationJoinAction() {
		return relationJoinAction;
	}
	
	public SortFilterAction getSortFilterAction() {
		return sortFilterAction;
	}
	
	public ReportButtonListener getReportButtonListener() {
		return reportButtonListener;
	}
	
	public SearchButtonListener getSearchButtonListener() {
		return searchButtonListener;
	}
	
	public DeleteButtonAction getDeleteButtonAction() {
		return deleteButtonAction;
	}
	
	public UpdateButtonAction getUpdateButtonAction() {
		return updateButtonAction;
	}
	
	public DoubleClickAction getDoubleClickAction() {
		return doubleClickAction;
	}

	public UpperPaneChangeListener getUpperPaneChangeListener() {
		return upperPaneChangeListener;
	}

	public AddButtonAction getAddButtonAction() {
		return addButtonAction;
	}

	public LowerPaneChangeListener getLowerPaneChangeListener() {
		return lowerPaneChangeListener;
	}

	public RefreshAction getRefreshAction() {
		return refreshAction;
	}

	
}

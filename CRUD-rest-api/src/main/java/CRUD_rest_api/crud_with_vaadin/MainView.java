package CRUD_rest_api.crud_with_vaadin;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import CRUD_rest_api.acessingdatajpa.Customer;
import CRUD_rest_api.acessingdatajpa.CustomerRepository;

@Route
public class MainView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	/**
	 * 
	 */

	private final CustomerRepository repo;

	private final CustomerEditor editor;

	final Grid<Customer> grid;

	final TextField filter;

	private final Button addNewBtn;

	public MainView(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
		

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);
		
		
		
	    // Configure and style components
	    setSpacing(true);
	    setAlignItems(Alignment.CENTER); // Centraliza os componentes verticalmente

	    // bind using naming convention
		
	    // Centraliza os botões horizontalmente
	    actions.setJustifyContentMode(JustifyContentMode.CENTER);

		grid.setHeight("300px");
		grid.getStyle().set("margin-top", "20px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		

		filter.setPlaceholder("Filter by last name");
		

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		} else {
			grid.setItems(repo.findAll());
		}
	}
	// end::listCustomers[]

}
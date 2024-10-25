package com.example.automate;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.ProductAdapter;
import model.CartManager;
import model.Product;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout navigatorDrawer, drawerLayout;
    ImageButton buttonDrawerToggle, buttonDrawerMenuRight;
    NavigationView navigationView;
    ImageView logo;
    String userId, userName, userEmail;
    TextView textUsername, textUserEmail, cart_count;


    // Fragment variables moved to Activity
    private ProductAdapter productAdapter;
    private RecyclerView recyclerViewProducts;
    private List<Product> productList = new ArrayList<>();
    private List<Product> filteredProductList = new ArrayList<>();

    private String currentCategory = "All";
    private String currentSearchQuery = "";
    private String currentSortOption = "Default";

    private EditText editTextMinPrice, editTextMaxPrice;
    private ImageButton arrowButton;
    private RadioGroup radioGroupRatings;
    private CheckBox checkBoxCategory1, checkBoxCategory2, checkBoxCategory3, checkBoxCategory4;
    private RadioButton radioRating1, radioRating2, radioRating3, radioRating4;
    private TextView textViewNoProducts;
    private Button buttonClearPriceFilter, buttonClearRatingFilter, buttonClearCategoryFilter;
    private LinearLayout categoryContainer, searchOutLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    Spinner spinnerSort;
    SearchView searchViewProducts;

    private int selectedRating = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        cart_count = findViewById(R.id.cart_count);
        cart_count.setText(String.valueOf(CartManager.getInstance().getCartCount()));

        navigatorDrawer = findViewById(R.id.navigatorDrawer);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        buttonDrawerMenuRight = findViewById(R.id.buttonDrawerMenuRight);
        navigationView = findViewById(R.id.navigationView);
        logo = findViewById(R.id.clickcart_logo);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigatorDrawer.open();
            }
        });

        // Handle logo click to load HomeFragment
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        View headerView = navigationView.getHeaderView(0);

        textUsername = headerView.findViewById(R.id.textUserName);
        textUserEmail = headerView.findViewById(R.id.textUserEmail);
        textUsername.setText("John Doe");
        textUserEmail.setText("johndoe@gmail.com");

        // Set OnClickListener for buttonDrawerMenuRight to load CartActivity
        buttonDrawerMenuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch CartActivity when buttonDrawerMenuRight is clicked
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Set up navigation item selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    // Load HomeActivity
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.navCart) {
                    // Launch CartActivity
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.navLogout) {
                    // Show confirmation dialog before logout
                    showLogoutConfirmationDialog();
                }

                navigatorDrawer.close();  // Close the drawer after selection
                return true;
            }
        });

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize other views
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        searchOutLayout = findViewById(R.id.searchOutLayout);
        Spinner spinnerSort = findViewById(R.id.spinnerSort);
        SearchView searchViewProducts = findViewById(R.id.searchViewProducts);
        editTextMinPrice = findViewById(R.id.editTextMinPrice);
        editTextMaxPrice = findViewById(R.id.editTextMaxPrice);
        radioGroupRatings = findViewById(R.id.radioGroupRatings);
        radioRating1 = findViewById(R.id.radioRating1);
        radioRating2 = findViewById(R.id.radioRating2);
        radioRating3 = findViewById(R.id.radioRating3);
        radioRating4 = findViewById(R.id.radioRating4);
        categoryContainer = findViewById(R.id.categoryContainer);
        textViewNoProducts = findViewById(R.id.textViewNoProducts);

        buttonClearPriceFilter = findViewById(R.id.buttonClearPriceFilter);
        buttonClearRatingFilter = findViewById(R.id.buttonClearRatingFilter);
        buttonClearCategoryFilter = findViewById(R.id.buttonClearCategoryFilter);

        // Set listeners for your filters
        buttonClearPriceFilter = findViewById(R.id.buttonClearPriceFilter);
        buttonClearRatingFilter = findViewById(R.id.buttonClearRatingFilter);
        buttonClearCategoryFilter = findViewById(R.id.buttonClearCategoryFilter);


        // Initialize arrow button and set onClickListener
        arrowButton = findViewById(R.id.arrowButton);
        arrowButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // Set OnClickListeners for each RadioButton
        radioRating1.setOnClickListener(v -> {
            selectedRating = 1;
            applyFilters();
        });
        radioRating2.setOnClickListener(v -> {
            selectedRating = 2;
            applyFilters();
        });
        radioRating3.setOnClickListener(v -> {
            selectedRating = 3;
            applyFilters();
        });
        radioRating4.setOnClickListener(v -> {
            selectedRating = 4;
            applyFilters();
        });

        // Clear Price Filter Button
        buttonClearPriceFilter.setOnClickListener(v -> {
            editTextMinPrice.setText("");
            editTextMaxPrice.setText("");
            applyFilters(); // Trigger apply filters
        });

        // Clear Rating Filter Button
        buttonClearRatingFilter.setOnClickListener(v -> {
            radioGroupRatings.clearCheck();
            selectedRating = 0;
            applyFilters(); // Trigger apply filters
        });

        // Clear Category Filter Button
        buttonClearCategoryFilter.setOnClickListener(v -> {
            clearCategoryFilters();
            applyFilters(); // Trigger apply filters
        });

        // Load product list
        loadProducts();

        // Setup the swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Remove search and filters
            searchViewProducts.setQuery("", false);
            spinnerSort.setSelection(0);
            selectedRating = 0;
            editTextMinPrice.setText("");
            editTextMaxPrice.setText("");
            radioGroupRatings.clearCheck();
            clearCategoryFilters();

            loadProducts();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Handle sorting selection
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentSortOption = parentView.getItemAtPosition(position).toString();
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                currentSortOption = "Default";
                applyFilters();
            }
        });

        // Handle product search
        searchViewProducts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchQuery = query;
                applyFilters();

                // Clear focus from the SearchView after submitting the query
                searchViewProducts.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                applyFilters();
                return true;
            }
        });

        // Set listeners for price edit texts to trigger applyFilters when text changes
        editTextMinPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                applyFilters();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editTextMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                applyFilters();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        searchOutLayout.setOnTouchListener((View v, @SuppressLint("ClickableViewAccessibility") MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (searchViewProducts.isFocused()) {
                    searchViewProducts.clearFocus();
                }
            }
            return false;
        });
        recyclerViewProducts.setOnTouchListener((View v, @SuppressLint("ClickableViewAccessibility") MotionEvent event) -> {
            // Clear focus if the touch event is outside the SearchView
            searchViewProducts.clearFocus();
            return false;
        });
        searchOutLayout.setOnTouchListener((View v, @SuppressLint("ClickableViewAccessibility") MotionEvent event) -> {
            // Clear focus if the touch event is outside the SearchView
            searchViewProducts.clearFocus();
            return false;
        });

    }

    // Method to show confirmation dialog
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();  // Close the dialog
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // This function applies the category, search, price, rating, and sorting filters
    private void applyFilters() {
        if (productList == null || productList.isEmpty()) {
            // Product list is not yet loaded, so return early
            return;
        }

        List<Product> tempFilteredList = new ArrayList<>(productList);

        // Step 1: Filter by search query
        if (!currentSearchQuery.isEmpty()) {
            List<Product> searchFilteredList = new ArrayList<>();
            for (Product product : tempFilteredList) {
                if (product.getName().toLowerCase().contains(currentSearchQuery.toLowerCase()) || product.getVendorName().toLowerCase().contains(currentSearchQuery.toLowerCase())) {
                    searchFilteredList.add(product);
                }
            }
            tempFilteredList = searchFilteredList;
        }

        // Step 2: Filter by price range
        double minPrice = getMinPrice();
        double maxPrice = getMaxPrice();
        if (minPrice >= 0 || maxPrice >= 0) {
            List<Product> priceFilteredList = new ArrayList<>();
            for (Product product : tempFilteredList) {
                if ((minPrice < 0 || product.getPrice() >= minPrice) && (maxPrice < 0 || product.getPrice() <= maxPrice)) {
                    priceFilteredList.add(product);
                }
            }
            tempFilteredList = priceFilteredList;
        }

        // Step 3: Filter by rating
        if (selectedRating > 0) {
            List<Product> ratingFilteredList = new ArrayList<>();
            for (Product product : tempFilteredList) {
                if (product.getRating() > selectedRating) {
                    ratingFilteredList.add(product);
                }
            }
            tempFilteredList = ratingFilteredList;
        }

        // Step 4: Filter by categories
        List<String> selectedCategories = getSelectedCategories();
        if (!selectedCategories.isEmpty()) {
            List<Product> categoryFilteredList = new ArrayList<>();
            for (Product product : tempFilteredList) {
                if (selectedCategories.contains(product.getProductCategoryName())) {
                    categoryFilteredList.add(product);
                }
            }
            tempFilteredList = categoryFilteredList;
        }

        // Step 5: Sort the filtered list
        switch (currentSortOption) {
            case "A-Z":
                Collections.sort(tempFilteredList, (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                break;
            case "Z-A":
                Collections.sort(tempFilteredList, (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName()));
                break;
            case "Price: Low to High":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(tempFilteredList, Comparator.comparingDouble(Product::getPrice));
                }
                break;
            case "Price: High to Low":
                Collections.sort(tempFilteredList, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;
            case "Default":
                break; // No sorting if default
        }

        // Update the filtered list and notify the adapter
        filteredProductList.clear();
        filteredProductList.addAll(tempFilteredList);
        productAdapter.notifyDataSetChanged();

        // Check if there are no filtered products, and display the "No Products" message
        if (filteredProductList.isEmpty()) {
            textViewNoProducts.setVisibility(View.VISIBLE);
            recyclerViewProducts.setVisibility(View.GONE);
        } else {
            textViewNoProducts.setVisibility(View.GONE);
            recyclerViewProducts.setVisibility(View.VISIBLE);
        }
    }

    private double getMinPrice() {
        String minPriceStr = editTextMinPrice.getText().toString();
        if (!minPriceStr.isEmpty()) {
            return Double.parseDouble(minPriceStr);
        }
        return -1; // Indicate no minimum price filter
    }

    private double getMaxPrice() {
        String maxPriceStr = editTextMaxPrice.getText().toString();
        if (!maxPriceStr.isEmpty()) {
            return Double.parseDouble(maxPriceStr);
        }
        return -1; // Indicate no maximum price filter
    }

    private List<String> getSelectedCategories() {
        List<String> selectedCategories = new ArrayList<>();

        // Iterate through all child views in the checkbox container
        for (int i = 0; i < categoryContainer.getChildCount(); i++) {
            View child = categoryContainer.getChildAt(i);
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                // If the checkbox is checked, add its text to the selected categories
                if (checkBox.isChecked()) {
                    selectedCategories.add(checkBox.getText().toString());
                }
            }
        }

        return selectedCategories;
    }

    private void clearCategoryFilters() {
        // Assuming you have a reference to the checkbox container
        for (int i = 0; i < categoryContainer.getChildCount(); i++) {
            View child = categoryContainer.getChildAt(i);
            if (child instanceof CheckBox) {
                ((CheckBox) child).setChecked(false);
            }
        }
    }


    // Method to load products
    private void loadProducts() {
        Log.d("ProductLoading", "Loading mock products");

        // Clear previous filtered product list
        if (filteredProductList != null) {
            filteredProductList.clear();
        }

        // Create mock product data
        productList = new ArrayList<>();

        productList.add(new Product("1", "Brake Pads", "https://neobrake.com/wp-content/uploads/2016/06/NeoBrake-Air-Disc-Brake-Pads-2.1.png", "category1",
                "High-quality brake pads for superior stopping power.", 49.99, 100, true,
                "vendor1", "2024-01-01", "2024-01-10", "Brakes", "AutoCorp", 4));

        productList.add(new Product("2", "Oil Filter", "https://res.cloudinary.com/knfilters-com/image/upload/c_lpad,dpr_2.0,f_auto,h_540,q_auto,w_540/v1/media/catalog/product/H/P/HP-1007-REV_2.jpg?_i=AB", "category2",
                "Oil filter for various car models.", 19.99, 150, true,
                "vendor2", "2024-01-01", "2024-01-10", "Filters", "PartsCo", 5));

        productList.add(new Product("3", "Spark Plugs", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHAvLF5RY893z8KAjXXdrlTn4JqSDAyqaW3w&s", "category3",
                "Durable spark plugs for better ignition.", 9.99, 200, true,
                "vendor3", "2024-01-01", "2024-01-10", "Ignition", "SparkPro", 3));

        productList.add(new Product("4", "Car Battery", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0BPa8MIIBTHCzvLkS69bnX-tR0CO9oacrfA&s", "category4",
                "Long-lasting car battery with high performance.", 129.99, 80, true,
                "vendor4", "2024-01-01", "2024-01-10", "Batteries", "PowerMax", 4));

        productList.add(new Product("5", "Tire Pressure Gauge", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6tR9TUsq7M0PkHxCdc8pVvrI9VhzJHrl4lA&s", "category5",
                "Essential tire pressure gauge for maintaining proper pressure.", 15.99, 120, true,
                "vendor5", "2024-01-01", "2024-01-10", "Tools", "GaugeMate", 2));

        // Initially, all products are displayed
        filteredProductList = new ArrayList<>(productList);
        productAdapter = new ProductAdapter(this, filteredProductList);
        recyclerViewProducts.setAdapter(productAdapter);

        // Extract distinct categories and create checkboxes
        createCategoryCheckboxes(getDistinctCategories(productList));

        // Log the successful setting of adapter
        Log.d("ProductLoading", "Product adapter set with " + filteredProductList.size() + " items.");

        // Apply filters to the loaded products
        applyFilters();
    }


    private List<String> getDistinctCategories(List<Product> products) {
        Set<String> categories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.getProductCategoryName()); // Assume categoryId represents the category
        }
        return new ArrayList<>(categories);
    }

    private void createCategoryCheckboxes(List<String> categories) {
        // Assuming you have a LinearLayout to hold the checkboxes
        categoryContainer.removeAllViews(); // Clear previous checkboxes if any

        for (String category : categories) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(category);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> applyFilters());
            categoryContainer.addView(checkBox);
        }
    }
}
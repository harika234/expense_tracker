package in.harika.expensetrackerapi.controller;

import in.harika.expensetrackerapi.entity.Expense;
import in.harika.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @GetMapping("/expenses")
        public Page<Expense> getAllExpenses(Pageable page) {
//            int number = 1;
//            calculateFactorial(number);
            return expenseService.getAllExpenses(page);
        }
    // we can pass the parameter in the url in the 2 ways
//    1) using the path variable
//    2) using the query string

//    path variable

    @GetMapping("/test")
    public  void testexpense() {
        System.out.println("testing api");
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id) {
        expenseService.deleteExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public  Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpenseDetails(expense);
//       return expenseService.saveExpenseDetails(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {
        return expenseService.updateExpenseDetails(id,expense);
    }

//    public int calculateFactorial(int number) {
//        return  number * calculateFactorial(number-1);
//    }
    @GetMapping("/expenses/category")
    public List<Expense> getExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readByCategory(category, page);
    }
    @GetMapping("/expenses/name")
    public List<Expense> getExpensesByName(@RequestParam String keyword, Pageable page) {
        return expenseService.readByName(keyword, page);
    }
    @GetMapping("/expenses/date ")
    public  List<Expense> getAllExpensesByDate(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            Pageable page) {
        return  expenseService.readByDate(startDate, endDate, page);
    }

}

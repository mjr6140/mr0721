package com.example.toolapp.restapi;

import com.example.toolapp.entities.Tool;
import com.example.toolapp.entities.ToolType;
import com.example.toolapp.exceptions.BadRequestException;
import com.example.toolapp.exceptions.ConflictingEntityException;
import com.example.toolapp.exceptions.NotFoundException;
import com.example.toolapp.models.RentalAgreement;
import com.example.toolapp.models.ToolCheckout;
import com.example.toolapp.repositories.ToolRepository;
import com.example.toolapp.repositories.ToolTypeRepository;
import com.example.toolapp.services.CheckoutService;
import com.example.toolapp.services.RentalAgreementFormatter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToolRestService {

    private ToolTypeRepository toolTypeRepository;
    private ToolRepository toolRepository;
    private CheckoutService checkoutService;

    @Autowired
    public ToolRestService(ToolTypeRepository toolTypeRepository,
                           ToolRepository toolRepository,
                           CheckoutService checkoutService) {
        this.toolTypeRepository = toolTypeRepository;
        this.toolRepository = toolRepository;
        this.checkoutService = checkoutService;
    }

    @Operation(summary = "Get a list of all available tool types")
    @GetMapping("/tool-types")
    List<ToolType> getToolTypes() {
        return toolTypeRepository.findAll();
    }

    @Operation(summary = "Create a new tool type")
    @PostMapping("/tool-types")
    ToolType createToolType(@RequestBody ToolType toolType) {
        toolType.setId(0);
        return toolTypeRepository.save(toolType);
    }

    @Operation(summary = "Get a list of all available tools")
    @GetMapping("/tools")
    List<Tool> findAllTools() {
        return toolRepository.findAll();
    }

    @Operation(summary = "Get a specific tool by ID")
    @GetMapping("/tools/{id}")
    Tool getToolById(@PathVariable long id) {
        return toolRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Operation(summary = "Update a tool by ID")
    @PutMapping("/tools/{id}")
    Tool updateTool(@PathVariable long id, @RequestBody Tool tool) {
        if (id != tool.getId()) {
            throw new BadRequestException();
        }

        Tool existing = toolRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return toolRepository.save(tool);
    }

    @Operation(summary = "Create a new tool")
    @PostMapping("/tools")
    Tool createTool(@RequestBody Tool tool) {
        tool.setId(0);

        Tool existingByCode = toolRepository.findByToolCode(tool.getToolCode());
        if (existingByCode != null) {
            throw new ConflictingEntityException();
        }

        Tool newTool = toolRepository.save(tool);
        return newTool;
    }

    /**
     * Checkout a tool and generate the rental agreement.  This returns the agreement in the specified text format.
     * You'd probably want to also allow retrieval of the raw JSON (or other formats) in a real implementation.
     *
     * @param toolCheckout
     * @return
     */
    @Operation(summary = "Checkout a tool and get the agreement response back as text")
    @PostMapping(value = "/checkout", produces = { "text/plain" } )
    String checkout(@RequestBody ToolCheckout toolCheckout) {
        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        return new RentalAgreementFormatter(rentalAgreement).format();
    }
}

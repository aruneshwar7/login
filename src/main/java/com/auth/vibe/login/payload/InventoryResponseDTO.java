package com.auth.vibe.login.payload;

import com.auth.vibe.login.model.InventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {
    private List<InventoryModel> content;
}

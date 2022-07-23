package com.quarantinefriends.dao;

import com.quarantinefriends.dto.BlockDTO;
import com.quarantinefriends.entity.Block;
import org.springframework.stereotype.Component;

@Component
public class BlockDao {

    public static BlockDTO mapToDTO(Block block) {
        BlockDTO blockDTO = new BlockDTO();
        if(block != null) {
            blockDTO.setId(block.getId());
            blockDTO.setFromUser(UserDao.mapToDTO(block.getFromUser()));
            blockDTO.setToUser(UserDao.mapToDTO(block.getToUser()));
        }
        return blockDTO;
    }

    public static Block mapToEntity(BlockDTO blockDTO) {
        Block block = new Block();
        if(blockDTO != null) {
            block.setId(blockDTO.getId());
            block.setFromUser(UserDao.mapToEntity(blockDTO.getFromUser()));
            block.setToUser(UserDao.mapToEntity(blockDTO.getToUser()));
        }
        return block;
    }
}

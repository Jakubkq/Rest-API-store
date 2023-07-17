package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }
    public Group updateGroup(Group group){

        Group groupToUpdate = groupRepository.findById(group.getId()).orElseThrow(null);
        if (group.getName() != null) {
            groupToUpdate.setName(group.getName());
        }
        saveGroup(groupToUpdate);
        return groupToUpdate;
    }
    public void deleteGroup(final Long groupId) {
        groupRepository.deleteById(groupId);
    }
}

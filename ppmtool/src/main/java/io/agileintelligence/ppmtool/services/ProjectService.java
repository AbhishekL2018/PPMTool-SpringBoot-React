package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdentifierException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return repository.save(project);
        }catch (Exception e){
            throw new ProjectIdentifierException("Project ID --> "+project.getProjectIdentifier()+
                    " already exists");
        }
    }

    public Project getProjectByIdentifier(String identifier){
            identifier = identifier.toUpperCase();
            Project returnValue = repository.findByProjectIdentifier(identifier);
            if(returnValue == null){
                throw new ProjectIdentifierException("Project ID --> "+identifier+
                        " doesn't exist");
            }
            return returnValue;
    }


    public List<Project> getAllProjects(){
        return repository.findAll();
    }


    public void deleteProject(String identifier){
        identifier = identifier.toUpperCase();
        Project returnValue = repository.findByProjectIdentifier(identifier);
        if(returnValue == null){
            throw new ProjectIdentifierException("Project ID --> "+identifier+
                    " doesn't exist");
        }
        repository.delete(returnValue);
    }
}

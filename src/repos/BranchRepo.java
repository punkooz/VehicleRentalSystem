package repos;

import entities.Branch;

import java.util.HashMap;

public class BranchRepo {

    HashMap<String, Branch> branchHashMap;

    public BranchRepo() {
        this.branchHashMap = new HashMap<>();
    }

    public HashMap<String, Branch> getBranchHashMap() {
        return branchHashMap;
    }

    public void setBranchHashMap(HashMap<String, Branch> branchHashMap) {
        this.branchHashMap = branchHashMap;
    }

    public void saveBranch(Branch branch){
        branchHashMap.put(branch.getBranchName(), branch);
    }

    public Branch getBranchByName(String branchName){
        return branchHashMap.get(branchName);
    }

}

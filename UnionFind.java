import java.util.ArrayList;

// A member for a union-find algorithm.
abstract class GMember {
    GLeader leader;
    
    GMember() {
        this.resetLeader();
    }
    
    // Set this member back to its default state for a new UnionFind search.
    // EFFECT: Sets leader to a new leader containing only this member.
    void resetLeader() {
        this.leader = new GLeader(this);
    }
    
    // Change the leader of this member.
    // EFFECT: Sets leader to the given leader.
    void setLeader(GLeader leader) {
        this.leader = leader;
        this.leader.addMember(this);
    }
    
    // Is this member in the same group as that one?
    boolean find(GMember that) {
        return that.leader.equals(this.leader);
    }
    
    // Unions the group this member is in with the group that member is in.
    // EFFECT: May result in the leader of this changing to the leader of that.
    void union(GMember that) {
        if (this.leader.groupSize() > that.leader.groupSize()) {
            that.leader.union(this.leader);
        } else {
            this.leader.union(that.leader);
        }
    }
}

// A leader for a union-find algorithm.
class GLeader {
    ArrayList<GMember> members;
    
    GLeader(GMember member) {
        this.members = new ArrayList<GMember>();
        this.members.add(member);
    }
    
    // Returns the size of this leader's group.
    int groupSize() {
        return this.members.size();
    }
    
    // Unions this leader's group with that leader's group.
    // EFFECT: Changes the leader of all group members to that leader,
    //         clears this leaader's member list.
    void union(GLeader that) {
        for (GMember gm : this.members) {
            gm.setLeader(that);
        }
        
        this.members.clear();
    }
    
    // Add the given member to this leader's group.
    // EFFECT: Adds the given member to this leader's members.
    void addMember(GMember member) {
        this.members.add(member);
    }
}

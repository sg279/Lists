package impl;

import java.util.*;

import common.InvalidIndexException;
import common.ListNode;
import interfaces.IListManipulator;
import interfaces.IOperator;
import interfaces.ITransformation;
import org.w3c.dom.ls.LSException;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        if (head==null){
            return 0;
        }
        else {
            return size(head.next)+1;
        }
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if(head==null){
            return false;
        }
        else if (head.element.equals(element)){
            return true;
        }
        else return contains(head.next, element);
    }

    @Override
    public int count(ListNode head, Object element) {
        int count = 0;
        if (head==null){
            return count;
        }
        else {
            if (head.element.equals(element)){
                return count(head.next,element)+1;
            }
            else{
                return count(head.next,element);
            }
        }
    }

    @Override
    public String convertToString(ListNode head) {
        if (head==null){
            return "";
        }
        if (head.next==null){
            return head.element.toString();
        }
        else{
            return head.element+","+convertToString(head.next);
        }
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {

        if (head==null||n<0){
            throw new InvalidIndexException();
        }
        else if (n==0){
            return head.element;
        }
        else{
            return getFromFront(head.next,n-1);
        }
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        int index = size(head)-n-1;
        if (index<0){
            throw new InvalidIndexException();
        }
        else if (index==0){
            return head.element;
        }
        else{
            return getFromFront(head.next,index-1);
        }
    }

    @Override
    public boolean deepEquals(ListNode head1, ListNode head2) {

        if(head1==null&&head2==null){
            return true;
        }
        else if(head1==null||head2==null){
            return false;
        }
        else if (head1.element!=head2.element){
            return false;
        }
        else{
            return deepEquals(head1.next,head2.next);
        }
    }

    @Override
    public ListNode deepCopy(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode newNode = new ListNode(head.element, deepCopy(head.next));
        return newNode;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if(head==null){
            return false;
        }
        else{
            if (contains(head.next,head.element)){
                return true;
            }
            else{
                return containsDuplicates(head.next);
            }
        }
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        if (head1==null){
            return head2;
        }
        if (head1.next==null){
            head1.next=head2;
            return head1;
        }
        else{
            head1.next=append(head1.next,head2);
        }
        return head1;
    }

    @Override
    public ListNode flatten(ListNode head) {
        if (head==null){
            return null;
        }
        //Check if the node's element is an instance of a list node object
        if(head.element instanceof ListNode){
            //A temporary pointer to the current node's next node
            ListNode temp = head.next;
            //Cast the current element to a list node and set the current node's element and next pointer to the element's properties
            head.next = ((ListNode) head.element).next;
            head.element = ((ListNode) head.element).element;
            //Add the temporary pointer to the end of the new list
            head=append(head, temp);

        }
        head.next = flatten(head.next);
        return head;
    }

    @Override
    public boolean isCircular(ListNode head) {
        if (head==null){
            return false;
        }
        ListNode visited = new ListNode(head);
        //Call the circular method with head as the start node, the head's next node as the current node
        //and visited as the start and end of the visited nodes list
        return circular(head, head.next, visited, visited);
    }

    /**
     * This method recursively goes through the list and adds nodes to a list of previously visited nodes, returning true
     * if a pointer to the start of the list is found, and false if the end of the list is reached or if another node in the list
     * that isn't the head is reached.
     * @param start The start of the list
     * @param current The current node
     * @param visitedHead The head of the list of visited nodes
     * @param visited The end of the list of visited nodes
     * @return If the list is circular or not
     */
    private boolean circular(ListNode start, ListNode current, ListNode visitedHead, ListNode visited){
        if(current==start){
            return true;
        }
        //Return false if the end of the list is reached or the list contains cycles but isn't circular
        else if (current==null||contains(visitedHead, current)){
            return false;
        }
        else{
            visited.next = new ListNode(current);
            return circular(start, current.next, visitedHead, visited.next);
        }
    }

    @Override
    public boolean containsCycles(ListNode head) {
        ListNode visited = new ListNode(head);
        return cycle(head, visited, visited);
    }

    /**
     * This method recursively goes through the list and adds nodes to a list of previously visited nodes, returning true
     * if a pointer to a node that already exists in the list is found, and false if the end of the list is reached.
     * @param start The start of the list
     * @param visitedHead The head of the list of visited nodes
     * @param visited The end of the list of visited nodes
     * @return Whether or not the list contains cycles
     */
    private boolean cycle(ListNode start, ListNode visitedHead, ListNode visited){
        if (start==null){
            return false;
        }
        else if(contains(visitedHead, start.next)){
            return true;
        }
        else{
            visited.next = new ListNode(start);
            return cycle(start.next, visitedHead, visited.next);
        }
    }

    @Override
    public ListNode sort(ListNode head, Comparator comparator) {
            int size = size(head);
            if (size<=1){
                return head;
            }
            else{
                //The node after the mid point of the list
                ListNode list2 = get(head,size/2);
                //The node before the mid point of the list
                ListNode list1 = get(head,(size/2)-1);
                //Split the list in half by removing the pointer to the end half of the list and setting list1 to the
                //head of the first half of the list (with the last half removed)
                list1.next = null;
                list1 = head;
                //Recursively call the sort function on each half of the list
                list1 = sort(list1, comparator);
                list2 = sort(list2, comparator);
                //Merge the sorted halves of the list
                return merge(list1, list2, comparator);
            }

    }

    /**
     * Gets the node at a certain point in the list
     * @param head The head of the list
     * @param n The index of the node to be retrieved
     * @return The node at the index n in the list
     */
    private ListNode get(ListNode head, int n){
        if (head==null||n<0){
            return null;
        }
        else if (n==0){
            return head;
        }
        else{
            return get(head.next,n-1);
        }
    }

    /**
     * This method takes two ordered lists and merges them into one sorted list
     * @param list1 The first list
     * @param list2 The second list
     * @param comparator
     * @return The merged list
     */
    private ListNode merge(ListNode list1, ListNode list2, Comparator comparator){
        //Create a new list called joined with an arbitrary node
        ListNode joined= new ListNode(1);
        ListNode current = joined;
        while(list1!=null&&list2!=null){
            //Add the first element from list 1 or list 2 to the joined list depending on which is bigger
            //And iterate though to the next item in that list
            if (comparator.compare(list1.element, list2.element)<0){
                current.next=list1;
                list1=list1.next;
            }
            else{
                current.next=list2;
                list2=list2.next;
            }
            current= current.next;
        }
        //After one list is empty add the remaining list
        if (list1==null){
            current.next=list2;
        }
        else{
            current.next = list1;
        }
        //Return joined from the second element onwards (as the first is arbitrary and needed for construction)
        return joined.next;

    }

    @Override
    public ListNode map(ListNode head, ITransformation transformation) {
        if(head==null){
            return null;
        }
        head.element = transformation.transform(head.element);
        head.next=map(head.next,transformation);
        return head;
    }

    @Override
    public Object reduce(ListNode head, IOperator operator, Object initial) {
        if(head==null){
            return initial;
        }
        else{
            return reduce(head.next,operator,operator.operate(head.element,initial));
        }
    }

}

package impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.InvalidIndexException;
import common.ListNode;
import interfaces.IListManipulator;
import interfaces.IOperator;
import interfaces.ITransformation;
import org.w3c.dom.NodeList;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 *
 */
public class IterativeListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        int size =0;
        ListNode current = head;
        while (current!=null){
            size++;
            current=current.next;
        }
        return size;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        ListNode current = head;
        while (current!=null){
            if (current.element.equals(element)){
                return true;
            }
            current=current.next;
        }
        return false;
    }

    @Override
    public int count(ListNode head, Object element) {
        int elementCount = 0;
        ListNode current = head;
        while (current!=null){
            if (current.element.equals(element)){
                elementCount++;
            }
            current=current.next;
        }
        return elementCount;
    }

    @Override
    public String convertToString(ListNode head) {
        String list = "";
        ListNode current = head;
        while (current!=null){
            list+=current.element.toString();
            if(current.next!=null){
                list+=",";
            }
            current=current.next;
        }
        return list;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        ListNode current = head;
        int index = 0;
        try{
            while(index<n){
                current=current.next;
                index++;
            }
            return current.element;
        }
        catch (NullPointerException e){
            throw new InvalidIndexException();
        }
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        ListNode current = head;
        int location = size(head)-n-1;
        if(location<0){
            throw new InvalidIndexException();
        }
        int index = 0;
        while(index<location){
            current=current.next;
            index++;
        }
        return current.element;
    }

    @Override
    public boolean deepEquals(ListNode head1, ListNode head2) {
        ListNode list1Current= head1;
        ListNode list2Current = head2;
        while (list1Current!=null&&list2Current!=null){
            if(list1Current.element!=list2Current.element){
                return false;
            }
            list1Current=list1Current.next;
            list2Current=list2Current.next;
        }
        if(list1Current!=null||list2Current!=null){
            return false;
        }
        return true;
    }

    @Override
    public ListNode deepCopy(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode list1Current= head;
        ListNode list2Head = new ListNode(list1Current.element);
        ListNode list2Current = list2Head;
        while(list1Current.next!=null){
            list1Current=list1Current.next;
            list2Current.next=new ListNode(list1Current.element);
            list2Current=list2Current.next;
        }
        return list2Head;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        while (head!=null){
            if (contains(head.next, head.element)){
                return true;
            }
            else{
                head=head.next;
            }
        }
        return false;
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        if(head1==null){
            return head2;
        }
        ListNode current = head1;
        while(current.next!=null){
            current=current.next;
        }
        current.next=head2;
        return head1;
    }

    @Override
    public ListNode flatten(ListNode head) {
        ListNode current = head;
        while(current!=null){

            if(current.element instanceof ListNode){
                ListNode temp = current.next;
                current.next = ((ListNode) current.element).next;
                current.element = ((ListNode) current.element).element;
                current = append(current,temp);

            }
            current=current.next;
        }
        return head;
    }

    @Override
    public boolean isCircular(ListNode head) {
        ListNode current = head;
        Set<ListNode> visited = new HashSet<>();
        while(current!=null){
            if(current.next==head){
                return true;
            }
            if(visited.contains(current.next)){
                return false;
            }
            visited.add(current);
            current=current.next;
        }
        return false;
    }

    @Override
    public boolean containsCycles(ListNode head) {
        ListNode current = head;
        Set<ListNode> visited = new HashSet<>();
        while(current!=null){
            if(visited.contains(current.next)){
                return true;
            }
            visited.add(current);
            current=current.next;
        }
        return false;
    }

    @Override
    public ListNode sort(ListNode head, Comparator comparator) {
        if (head==null){
            return null;
        }
        boolean swapsMade=true;

        while (swapsMade){
            swapsMade=false;
            ListNode current = head;
            while (current.next!=null) {
                if (comparator.compare(current.element, current.next.element) > 0) {
                    Object temp = current.element;
                    current.element = current.next.element;
                    current.next.element = temp;
                    swapsMade = true;
                }
                current = current.next;
            }
        }
        return head;
    }

    @Override
    public ListNode map(ListNode head, ITransformation transformation) {
        ListNode current = head;
        while(current!=null){
            current.element=transformation.transform(current.element);
            current=current.next;
        }
        return head;
    }

    @Override
    public Object reduce(ListNode head, IOperator operator, Object initial) {
        ListNode current = head;
        while(current!=null){
            initial = operator.operate(initial, current.element);
            current=current.next;
        }
        return initial;
    }

}

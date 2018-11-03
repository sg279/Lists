package impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import common.InvalidIndexException;
import common.ListNode;
import interfaces.IListManipulator;
import interfaces.IOperator;
import interfaces.ITransformation;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        int size = 0;
        if (head==null){
            return size;
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
        if (head==null||index<0){
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
        return null;
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
        /*if (head1==null){
            return head2;
        }
        if (head1.next==null){
            head1.next=head2;
            return head1;
        }
        else{
            head1.next=append(head1.next,head2);
        }
        return head1;*/

        if (head1==null){
            return head2;
        }
        ListNode head3 = head1.next;
        if (head3==null){
            head1.next=head2;
            return head1;
        }
        if (head3.next==null){
            head3.next=head2;
            return head1;
        }
        else{
            return append(head3.next,head2);
        }
    }

    @Override
    public ListNode flatten(ListNode head) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCircular(ListNode head) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsCycles(ListNode head) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ListNode sort(ListNode head, Comparator comparator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListNode map(ListNode head, ITransformation transformation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object reduce(ListNode head, IOperator operator, Object initial) {
        // TODO Auto-generated method stub
        return null;
    }

}

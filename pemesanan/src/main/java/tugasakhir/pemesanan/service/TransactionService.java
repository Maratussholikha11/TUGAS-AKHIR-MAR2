package tugasakhir.pemesanan.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
//import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.*;
import tugasakhir.pemesanan.repository.OrderingRepository;
import tugasakhir.pemesanan.repository.StatusRepository;
import tugasakhir.pemesanan.repository.TransactionRepository;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderingRepository orderingRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Transaction save(Transaction transaction){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("user adalah :" + user.getName());
        Ordering ord = orderingRepository.getById(transaction.getOrderId());
        Transaction current = new Transaction();
        current.setOrdering(ord);
        current.setOrderId(ord.getId_Order());
        current.setTransactionDate(transaction.getTransactionDate());
        current.setImage(transaction.getImage()); //receipt
        current.setReceiptName(transaction.getReceiptName());
        current.setNote(transaction.getNote());
        current.setLunas(transaction.getLunas());
        current.setTransactionDate(transaction.getTransactionDate());
        current.setTotalPay(transaction.getTotalPay());
        Status s = statusRepository.findStatusByName("Pending");
        System.out.println("status is : " + s.getName());
        current.setStatus(s);
        current.setUser(user);
        transaction = current;
        return transactionRepository.save(transaction);
    }

    // select Order by user
    public List<Transaction> findOrderingByUser(Integer idUser){
        return transactionRepository.findOrderingByUser(idUser);
    }

    public Transaction approve(Transaction transaction){
        Status s = statusRepository.findStatusByName("Approved");
        transaction.setStatus(s);
        System.out.println("transaction with id " + transaction.getId_transaction() +" has been approved");
        return transactionRepository.save(transaction);
    }

    public Transaction reject(Transaction transaction){
        Status s = statusRepository.findStatusByName("Rejected");
        transaction.setNote(transaction.getNote());
        transaction.setStatus(s);
        System.out.println("transaction with id " + transaction.getId_transaction() +" has been rejected");
        System.out.println("note refusal : " + transaction.getNote());
        return transactionRepository.save(transaction);
    }
}

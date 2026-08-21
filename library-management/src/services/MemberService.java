package services;

import entities.person.Member;
import exceptions.LibraryException;
import exceptions.MemberNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gerenciamento de Membros — funcionalidade do enunciado.
 *
 * Funcionalidades implementadas:
 *   register(Member)                   cadastrar membro
 *   update(matrícula, nome, email)     editar membro existente
 *   list()                             listar todos os membros
 *   findById(matrícula)                localizar um membro pela matrícula
 *
 * A matrícula é única e serve de identificador nas demais operações — é por ela
 * que LoanService encontra o membro ao realizar um empréstimo.
 */
public class MemberService {

    private final List<Member> members = new ArrayList<>();

    public void register(Member member) {
        for (Member existing : members) {
            if (existing.getId().equalsIgnoreCase(member.getId())) {
                throw new LibraryException(
                        "Já existe um membro cadastrado com a matrícula " + member.getId() + "."
                );
            }
        }
        members.add(member);
    }

    public void update(String id, String name, String email) {
        Member member = findById(id);
        member.setName(name);
        member.setEmail(email);
    }

    // Somente leitura: o cadastro só muda por register().
    public List<Member> list() {
        return Collections.unmodifiableList(members);
    }

    public Member findById(String id) {
        for (Member member : members) {
            if (member.getId().equalsIgnoreCase(id)) {
                return member;
            }
        }
        throw new MemberNotFoundException("Nenhum membro encontrado com a matrícula " + id + ".");
    }
}

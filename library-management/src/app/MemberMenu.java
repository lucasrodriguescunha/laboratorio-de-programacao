package app;

import entities.person.Member;
import services.MemberService;

/**
 * Submenu de membros: cadastrar, editar e listar quem pode tomar livros
 * emprestados.
 */
public class MemberMenu extends Menu {

    private final MemberService memberService;

    public MemberMenu(MemberService memberService, ConsoleInput input) {
        super(input);
        this.memberService = memberService;
    }

    @Override
    protected String title() {
        return "----- MEMBROS -----";
    }

    @Override
    protected String[] options() {
        return new String[]{
                "Cadastrar membro",
                "Editar membro",
                "Listar membros"
        };
    }

    @Override
    protected void execute(int option) {
        switch (option) {
            case 1:
                register();
                break;
            case 2:
                update();
                break;
            case 3:
                list();
                break;
        }
    }

    private void register() {
        System.out.println();
        String id = input.readText("Matrícula: ");
        String name = input.readText("Nome: ");
        String email = input.readText("Email: ");

        memberService.register(new Member(id, name, email));
        System.out.println("Membro cadastrado com sucesso.");
    }

    private void update() {
        System.out.println();
        String id = input.readText("Matrícula do membro: ");
        String name = input.readText("Novo nome: ");
        String email = input.readText("Novo email: ");

        memberService.update(id, name, email);
        System.out.println("Membro atualizado com sucesso.");
    }

    private void list() {
        System.out.println();
        System.out.println("Membros cadastrados:");
        describeAll(memberService.list());
    }
}

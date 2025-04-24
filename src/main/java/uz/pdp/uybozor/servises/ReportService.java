package uz.pdp.uybozor.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.DTO.ReportDTO;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Report;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.PostRepository;
import uz.pdp.uybozor.repo.ReportRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public Report createReport(ReportDTO dto) {
        Users reportedUser = usersRepository.findById(dto.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        Users reportedBy = usersRepository.findById(dto.getReportedById())
                .orElseThrow(() -> new RuntimeException("Reported by user not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Report report = new Report();
        report.setReportedUser(reportedUser);
        report.setPost(post);
        report.setMessage(dto.getMessage());
        report.setDate(new Date());
        report.setReportedBy(reportedBy);

        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReport(Integer id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public Report updateReport(Integer id, ReportDTO dto) {
        Report report = getReport(id);

        report.setMessage(dto.getMessage());
        report.setDate(new Date());

        if (dto.getPostId() != null) {
            Post post = postRepository.findById(dto.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            report.setPost(post);
        }

        return reportRepository.save(report);
    }

    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }
}


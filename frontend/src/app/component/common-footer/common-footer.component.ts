import { Component } from '@angular/core';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faGithub, faLinkedin, faInstagram } from "@fortawesome/free-brands-svg-icons";
import { faAt } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-common-footer',
  imports: [FontAwesomeModule],
  templateUrl: './common-footer.component.html',
  styleUrl: './common-footer.component.css'
})

export class CommonFooterComponent {
	protected faInstagram = faInstagram;
	protected faGithub = faGithub;
	protected faLinkedin = faLinkedin;
	protected faAt = faAt;
}

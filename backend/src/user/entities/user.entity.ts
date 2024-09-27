import { UserDetails } from 'src/user-detail/entities/user-detail.entity';
import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  OneToOne,
  JoinColumn,
} from 'typeorm';

@Entity()
export class User {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  name: string;

  @Column()
  email: string;

  @Column({ nullable: false })
  password: string;

  @Column({ default: true })
  isActive: boolean;

  @OneToOne(() => UserDetails, { cascade: true })
  @JoinColumn()
  userDetails: UserDetails;
}

import { User } from 'src/user/entities/user.entity';
import { Entity, Column, PrimaryGeneratedColumn, OneToOne } from 'typeorm';

@Entity()
export class UserDetails {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ type: 'simple-array', nullable: true })
  allergens: string[];

  @Column({ nullable: false })
  age: number;

  @Column({ nullable: true })
  gender: string;

  @Column({ nullable: true })
  height: number;

  @Column({ nullable: true })
  weight: number;

  @Column({ nullable: true })
  dietPreference: string;

  @OneToOne(() => User, (user) => user.userDetails)
  user: User;
}
